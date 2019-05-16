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
package no.priv.bang.handlereg.db.liquibase;

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
        addAccounts(connection);
        assertAccounts(connection);
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
        Integer jodAccountid = addAccount(connection, "jod");
        Integer jadAccountid = addAccount(connection, "jad");
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

        Map<String, Integer> accountids = new HashMap<>();
        accountids.put("jod", jodAccountid);
        accountids.put("jad", jadAccountid);
        Map<String, Integer> storeids = findStoreIds(connection);
        assertEquals(135, storeids.size());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try(PrintWriter transactionWriter = new PrintWriter("transactions.sql")) {
            transactionWriter.println("--liquibase formatted sql");
            transactionWriter.println("--changeset sb:example_users");
            for (Handling handling : oldData.handlinger) {
                int accountid = accountids.get(handling.username);
                System.out.println("handling: " + handling);
                int storeid = storeids.get(handling.butikk);
                double belop = handling.belop;
                String timestamp = format.format(handling.timestamp);
                transactionWriter.println(String.format("insert into transactions (store_id, transaction_time, transaction_amount) values (%d, %d, '%s', %f);", accountid, storeid, timestamp, belop));
            }
        }
    }

    private void addAccounts(Connection connection) throws Exception {
        addAccount(connection, "admin");
    }

    private void assertAccounts(Connection connection) throws Exception {
        String sql = "select count(*) from accounts";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            try(ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    int count = results.getInt(1);
                    assertEquals(1, count);
                }
            }
        }
    }

    private int addAccount(Connection connection, String username) throws Exception {
        String sql = "insert into accounts (username) values (?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.executeUpdate();
        }

        return findAccountId(connection, username);
    }

    private int findAccountId(Connection connection, String username) throws Exception {
        String sql = "select account_id from accounts where username=?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try(ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    return results.getInt(1);
                }
            }
        }

        return -1;
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
        try(PreparedStatement statement = connection.prepareStatement("select * from transactions join stores on transactions.store_id=stores.store_id join accounts on transactions.account_id=accounts.account_id")) {
            ResultSet results = statement.executeQuery();
            assertTransaction(results, 210.0, "Joker Folldal", "admin");
        }
    }

    private void addTransactions(Connection connection) throws Exception {
        int accountid = 1;
        int storeid = 1;
        addTransaction(connection, accountid, storeid, 210.0);
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

    private void addTransaction(Connection connection, int accountid, int storeid, double amount) throws Exception {
        try(PreparedStatement statement = connection.prepareStatement("insert into transactions (account_id, store_id, transaction_amount) values (?, ?, ?)")) {
            statement.setInt(1, accountid);
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
