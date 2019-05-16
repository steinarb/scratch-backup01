/*
 * Copyright 2018 Steinar Bang
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
package no.bang.priv.handlereg.db.derby.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.ops4j.pax.jdbc.derby.impl.DerbyDataSourceFactory;
import org.osgi.service.jdbc.DataSourceFactory;

import no.bang.priv.handlereg.services.HandleregException;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class HandleregDerbyTestDatabaseTest {

    @Test
    void testCreateAndVerifySomeDataInSomeTables() throws Exception {
        DataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        MockLogService logservice = new MockLogService();
        HandleregDerbyTestDatabase database = new HandleregDerbyTestDatabase();
        database.setLogService(logservice);
        database.setDataSourceFactory(dataSourceFactory);
        database.activate();
        assertAccounts(database);
        int originalNumberOfTransactions = findNumberOfTransactions(database);
        addTransaction(database, 138);
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
                HandleregDerbyTestDatabase database = new HandleregDerbyTestDatabase();
                database.setLogService(logservice);
                database.setDataSourceFactory(dataSourceFactory);
                database.activate();
                fail("Should never get here");
            });
    }

    @Test
    public void testForceReleaseLocks() {
        DataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        MockLogService logservice = new MockLogService();
        HandleregDerbyTestDatabase database = new HandleregDerbyTestDatabase();
        database.setLogService(logservice);
        database.setDataSourceFactory(dataSourceFactory);
        database.activate();

        boolean result = database.forceReleaseLiquibaseLock();
        assertTrue(result);
    }

    @Test
    public void testForceReleaseLocksFailing() throws Exception {
        // Need a real database to create the object
        DataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        MockLogService logservice = new MockLogService();
        HandleregDerbyTestDatabase database = new HandleregDerbyTestDatabase();
        database.setLogService(logservice);
        database.setDataSourceFactory(dataSourceFactory);
        database.activate();

        // Replace the mock datasource returning a connection that will fail when
        // releasing the lock
        Connection connection = mock(Connection.class);
        DataSource datasource = mock(DataSource.class);
        when(datasource.getConnection()).thenReturn(connection);
        database.dataSource = datasource;

        boolean result = database.forceReleaseLiquibaseLock();
        assertFalse(result);
    }

    private void assertAccounts(HandleregDerbyTestDatabase database) throws Exception {
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("select * from accounts")) {
                try (ResultSet results = statement.executeQuery()) {
                    assertAccount(results, "jod");
                }
            }
        }
    }

    private void assertAccount(ResultSet results, String username) throws Exception {
        assertTrue(results.next());
        assertEquals(username, results.getString(2)); // column 1 is the id
    }

    private void addTransaction(HandleregDerbyTestDatabase database, double amount) throws SQLException {
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("insert into transactions (account_id, store_id, transaction_amount) values (1, 1, ?)")) {
                statement.setDouble(1, amount);
                statement.executeUpdate();
            }
        }
    }

    private int findNumberOfTransactions(HandleregDerbyTestDatabase database) throws SQLException {
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
