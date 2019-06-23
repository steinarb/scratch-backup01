/*
 * Copyright 2019 Steinar Bang
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
package no.priv.bang.handlereg.db.postgresql;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.ops4j.pax.jdbc.derby.impl.DerbyDataSourceFactory;
import org.osgi.service.jdbc.DataSourceFactory;

import no.priv.bang.handlereg.services.HandleregException;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;
import static no.priv.bang.handlereg.services.HandleregConstants.*;

class HandleregPostgresqlDatabaseTest {

    @Test
    void testCreateAndVerifySomeDataInSomeTables() throws Exception {
        DataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        MockLogService logservice = new MockLogService();
        HandleregPostgresqlDatabase database = new HandleregPostgresqlDatabase();
        database.setLogService(logservice);
        database.setDataSourceFactory(dataSourceFactory);
        Map<String, Object> config = new HashMap<>();
        config.put(HANDLEREG_JDBC_URL, "jdbc:derby:memory:ukelonn;create=true");
        database.activate(config);
        int jdId = addAccount(database, "jd");
        assertAccounts(database);
        int storeid = addStore(database, "Spar Næroset");
        int originalNumberOfTransactions = findNumberOfTransactions(database);
        addTransaction(database, jdId, storeid, 138);
        int updatedNumberOfTransactions = findNumberOfTransactions(database);
        assertEquals(originalNumberOfTransactions + 1, updatedNumberOfTransactions);

        // Connection brukes av Shiro JdbcRealm
        DataSource datasource = database.getDatasource();
        assertEquals("Apache Derby", datasource.getConnection().getMetaData().getDatabaseProductName());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testFailDuringActivation() {
        assertThrows(HandleregException.class, () -> {
                DataSourceFactory dataSourceFactory = mock(DataSourceFactory.class);
                when(dataSourceFactory.createConnectionPoolDataSource(any())).thenThrow(SQLException.class);
                MockLogService logservice = new MockLogService();
                HandleregPostgresqlDatabase database = new HandleregPostgresqlDatabase();
                database.setLogService(logservice);
                database.setDataSourceFactory(dataSourceFactory);
                Map<String, Object> config = null;
                database.activate(config);
                fail("Should never get here");
            });
    }

    @Test
    public void testForceReleaseLocks() {
        DataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        MockLogService logservice = new MockLogService();
        HandleregPostgresqlDatabase database = new HandleregPostgresqlDatabase();
        database.setLogService(logservice);
        database.setDataSourceFactory(dataSourceFactory);
        Map<String, Object> config = new HashMap<>();
        config.put(HANDLEREG_JDBC_URL, "jdbc:derby:memory:ukelonn;create=true");
        database.activate(config);

        boolean result = database.forceReleaseLiquibaseLock();
        assertTrue(result);
    }

    @Test
    public void testForceReleaseLocksFailing() throws Exception {
        // Need a real database to create the object
        DataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        MockLogService logservice = new MockLogService();
        HandleregPostgresqlDatabase database = new HandleregPostgresqlDatabase();
        database.setLogService(logservice);
        database.setDataSourceFactory(dataSourceFactory);
        Map<String, Object> config = new HashMap<>();
        config.put(HANDLEREG_JDBC_URL, "jdbc:derby:memory:ukelonn;create=true");
        database.activate(config);

        // Replace the mock datasource returning a connection that will fail when
        // releasing the lock
        Connection connection = mock(Connection.class);
        DataSource datasource = mock(DataSource.class);
        when(datasource.getConnection()).thenReturn(connection);
        database.dataSource = datasource;

        boolean result = database.forceReleaseLiquibaseLock();
        assertFalse(result);
    }

    private void assertAccounts(HandleregPostgresqlDatabase database) throws Exception {
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("select * from accounts")) {
                try (ResultSet results = statement.executeQuery()) {
                    assertAccount(results, "jd");
                }
            }
        }
    }

    private void assertAccount(ResultSet results, String username) throws Exception {
        assertTrue(results.next());
        assertEquals(username, results.getString(2)); // column 1 is the id
    }

    private int addAccount(HandleregPostgresqlDatabase database, String username) throws Exception {
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("insert into accounts (username) values (?)")) {
                statement.setString(1, username);
                statement.executeUpdate();
            }
        }
        int accountId = -1;
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("select * from accounts where username=?")) {
                statement.setString(1, username);
                try (ResultSet results = statement.executeQuery()) {
                    results.next();
                    accountId = results.getInt(1);
                }
            }
        }
        return accountId;
    }

    private int addStore(HandleregPostgresqlDatabase database, String storename) throws Exception {
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("insert into stores (store_name) values (?)")) {
                statement.setString(1, storename);
                statement.executeUpdate();
            }
        }
        int storeid = -1;
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("select * from stores where store_name=?")) {
                statement.setString(1, storename);
                try (ResultSet results = statement.executeQuery()) {
                    results.next();
                    storeid = results.getInt(1);
                }
            }
        }
        return storeid;
    }

    private void addTransaction(HandleregPostgresqlDatabase database, int accountid, int storeid, double amount) throws SQLException {
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("insert into transactions (account_id, store_id, transaction_amount) values (?, ?, ?)")) {
                statement.setInt(1, accountid);
                statement.setInt(2, storeid);
                statement.setDouble(3, amount);
                statement.executeUpdate();
            }
        }
    }

    private int findNumberOfTransactions(HandleregPostgresqlDatabase database) throws SQLException {
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("select * from transactions")) {
                try (ResultSet results = statement.executeQuery()) {
                    int count = 0;
                    while(results.next()) {
                        ++count;
                    }

                    return count;
                }
            }
        }
    }

}
