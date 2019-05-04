/*
 * Copyright 2018-2019 Steinar Bang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations
 * under the License.
 */
package no.bang.priv.handlereg.db.liquibase;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.io.PrintWriter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.ops4j.pax.jdbc.derby.impl.DerbyDataSourceFactory;
import org.osgi.service.jdbc.DataSourceFactory;

class HandleregLiquibaseTest {
    DataSourceFactory derbyDataSourceFactory = new DerbyDataSourceFactory();

    @Test
    void testCreateSchema() throws Exception {
        Connection connection = createConnection();
        HandleregLiquibase handleregLiquibase = new HandleregLiquibase();
        handleregLiquibase.createInitialSchema(connection);
        addUsers(connection);
        assertUsers(connection);
        addStores(connection);
        assertStores(connection);
        addTransactions(connection);
        assertTransactions(connection);
        handleregLiquibase.updateSchema(connection);
    }

    @Test
    void testForceReleaseLocks() throws Exception {
        Connection connection = createConnection();
        HandleregLiquibase handleregLiquibase = new HandleregLiquibase();
        handleregLiquibase.forceReleaseLocks(connection);
        // Nothing to test for but if we get here, no exceptions have been thrown
    }

    @Disabled("Pseudo-test that imports legacy data and turns them into SQL files that can be imported into an SQL database")
    @Test
    public void createSqlFromOriginalData() throws Exception {
        Connection connection = createConnection();
        HandleregLiquibase handleregLiquibase = new HandleregLiquibase();
        handleregLiquibase.createInitialSchema(connection);
        OldData oldData = new OldData();
        assertEquals(135, oldData.butikker.size());
        assertEquals(4354, oldData.handlinger.size());
        addUser(connection, "jd", "johndoe21@gmail.com", "John", "Doe", "secret", "salt");
        addUser(connection, "jad", "janedoe21@gmail.com", "Jane", "Doe", "secret", "salt");
        int nærbutikkRekkefølge = 0;
        int annenbutikkRekkefølge = 0;
        int gruppe = 1;
        int rekkefølge = 0;
        for (String store : oldData.butikker) {
            if (oldData.nærbutikker.contains(store)) {
                gruppe = 1;
                rekkefølge = (nærbutikkRekkefølge += 10);
            } else {
                gruppe = 2;
                rekkefølge = (annenbutikkRekkefølge += 10);
            }
            addStore(connection, store, gruppe, rekkefølge);
        }

        Map<String, Integer> userids = findUserids(connection);
        assertEquals(2, userids.size());
        Map<String, Integer> storeids = findStoreIds(connection);
        assertEquals(135, storeids.size());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try(PrintWriter transactionWriter = new PrintWriter("transactions.sql")) {
            transactionWriter.println("--liquibase formatted sql");
            transactionWriter.println("--changeset sb:example_users");
            for (Handling handling : oldData.handlinger) {
                int userid = userids.get(handling.username);
                System.out.println("handling: " + handling);
                int storeid = storeids.get(handling.butikk);
                double belop = handling.belop;
                String timestamp = format.format(handling.timestamp);
                transactionWriter.println(String.format("insert into transactions (user_id, store_id, transaction_time, transaction_amount) values (%d, %d, '%s', %f);", userid, storeid, timestamp, belop));
            }
        }
    }

    private Map<String, Integer> findStoreIds(Connection connection) throws Exception {
        Map<String, Integer> storeids = new HashMap<>();
        try(PrintWriter storeWriter = new PrintWriter("stores.sql")) {
            storeWriter.println("--liquibase formatted sql");
            storeWriter.println("--changeset sb:example_stores");
            try(PreparedStatement statement = connection.prepareStatement("select * from stores")) {
                ResultSet results = statement.executeQuery();
                while(results.next()) {
                    String storename = results.getString(2);
                    Integer storeid = results.getInt(1);
                    Integer gruppe = results.getInt(3);
                    Integer rekkefølge = results.getInt(4);
                    storeids.put(storename, storeid);
                    storeWriter.println(String.format("insert into stores (store_name, gruppe, rekkefolge) values ('%s', %d, %d);", storename, gruppe, rekkefølge));
                }
            }
        }

        return storeids;
    }

    private Map<String, Integer> findUserids(Connection connection) throws Exception {
        Map<String, Integer> userids = new HashMap<>();
        try(PrintWriter usersWriter = new PrintWriter("users.sql")) {
            usersWriter.println("--liquibase formatted sql");
            usersWriter.println("--changeset sb:example_users");
            try(PrintWriter passwordsWriter = new PrintWriter("passwords.sql")) {
                passwordsWriter.println("--liquibase formatted sql");
                passwordsWriter.println("--changeset sb:example_passwords");
                try(PreparedStatement statement = connection.prepareStatement("select * from users join password on users.user_id=password.user_id")) {
                    ResultSet results = statement.executeQuery();
                    while(results.next()) {
                        String username = results.getString(2);
                        Integer userid = results.getInt(1);
                        userids.put(username, userid);
                        String email = results.getString(3);
                        String firstname = results.getString(4);
                        String lastname = results.getString(5);
                        String password = results.getString(8);
                        String salt = results.getString(9);
                        usersWriter.println(String.format("insert into users (username, email, firstname, lastname) values ('%s', '%s', '%s', '%s');", username, email, firstname, lastname));
                        passwordsWriter.println(String.format("insert into password (user_id, username, password, salt) values (%d, '%s', '%s', '%s');", userid, username, password, salt));
                    }
                }
            }
        }

        return userids;
    }

    private void addUsers(Connection connection) throws Exception {
        addUser(connection, "admin", "admin@gmail.com", "Admin", "Istrator", "pepper", "salt");
    }

    private void assertUsers(Connection connection) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("select * from users join password on users.user_id=password.user_id")) {
            ResultSet results = statement.executeQuery();
            assertUser(results, "admin", "admin@gmail.com", "Admin", "Istrator", "pepper", "salt");
        }
    }

    private void addStores(Connection connection) throws Exception {
        addStore(connection, "Joker Folldal", 2, 10);
    }

    private void assertStores(Connection connection) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("select * from stores")) {
            ResultSet resultset = statement.executeQuery();
            assertStore(resultset, "Joker Folldal");
        }
    }

    private void assertTransactions(Connection connection) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("select * from transactions join stores on transactions.store_id=stores.store_id join users on transactions.user_id=users.user_id")) {
            ResultSet results = statement.executeQuery();
            assertTransaction(results, 210.0, "Joker Folldal", "admin");
        }
    }

    private void addTransactions(Connection connection) throws Exception {
        int userid = 1;
        int storeid = 1;
        addTransaction(connection, userid, storeid, 210.0);
    }

    private void addUser(Connection connection, String username, String email, String firstname, String lastname, String password, String salt) throws Exception {
        String userSql = "insert into users (username, email, firstname, lastname) values (?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(userSql)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, firstname);
            statement.setString(4, lastname);
            System.out.println("sql: " + statement.toString());
            statement.executeUpdate();
        }

        int userid = -1;
        try(PreparedStatement queryForUserid = connection.prepareStatement("select user_id from users where username=?")) {
            queryForUserid.setString(1, username);
            ResultSet results = queryForUserid.executeQuery();
            results.next();
            userid = results.getInt(1);
        }

        String passwordSql = "insert into password (user_id, username, password, salt) values (?, ?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(passwordSql)) {
            statement.setInt(1, userid);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, salt);
            statement.executeUpdate();
        }
    }

    private void assertUser(ResultSet results, String username, String email, String firstname, String lastname, String password, String salt) throws Exception {
        assertTrue(results.next());
        assertEquals(username, results.getString(2)); // column 1 is the id
        assertEquals(email, results.getString(3));
        assertEquals(firstname, results.getString(4));
        assertEquals(lastname, results.getString(5));
        assertEquals(password, results.getString(8));
        assertEquals(salt, results.getString(9));
    }

    private void addStore(Connection connection, String storename, int gruppe, int rekkefølge) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("insert into stores (store_name, gruppe, rekkefolge) values (?, ?, ?)")) {
            statement.setString(1, storename);
            statement.setInt(2, gruppe);
            statement.setInt(3, rekkefølge);
            statement.executeUpdate();
        }
    }

    private void assertStore(ResultSet resultset, String storename) throws Exception {
        assertTrue(resultset.next());
        assertEquals(storename, resultset.getString(2));
    }

    private void addTransaction(Connection connection, int userid, int storeid, double amount) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("insert into transactions (user_id, store_id, transaction_amount) values (?, ?, ?)")) {
            statement.setInt(1, userid);
            statement.setInt(2, storeid);
            statement.setDouble(3, amount);
            statement.executeUpdate();
        }
    }

    private void assertTransaction(ResultSet results, double amount, String storename, String username) throws Exception {
        assertTrue(results.next());
        assertEquals(amount, results.getDouble(5), 0.1);
        assertEquals(storename, results.getString(7));
        assertEquals(username, results.getString(11));
    }

    private Connection createConnection() throws Exception {
        Properties properties = new Properties();
        properties.setProperty(DataSourceFactory.JDBC_URL, "jdbc:derby:memory:ukelonn;create=true");
        DataSource dataSource = derbyDataSourceFactory.createDataSource(properties);
        return dataSource.getConnection();
    }

}
