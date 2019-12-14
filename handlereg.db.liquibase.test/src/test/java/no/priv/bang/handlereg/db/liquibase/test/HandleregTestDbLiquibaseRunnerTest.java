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
package no.priv.bang.handlereg.db.liquibase.test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;
import org.junit.jupiter.api.Test;
import org.ops4j.pax.jdbc.derby.impl.DerbyDataSourceFactory;
import org.osgi.service.jdbc.DataSourceFactory;

import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class HandleregDerbyTestDatabaseTest {

    @Test
    void testCreateAndVerifySomeDataInSomeTables() throws Exception {
        DataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        Properties properties = new Properties();
        properties.setProperty(DataSourceFactory.JDBC_URL, "jdbc:derby:memory:handlereg;create=true");
        DataSource datasource = dataSourceFactory.createDataSource(properties);

        MockLogService logservice = new MockLogService();
        HandleregTestDbLiquibaseRunner runner = new HandleregTestDbLiquibaseRunner();
        runner.setLogService(logservice);
        runner.activate();
        runner.prepare(datasource);
        assertAccounts(datasource);
        int originalNumberOfTransactions = findNumberOfTransactions(datasource);
        addTransaction(datasource, 138);
        int updatedNumberOfTransactions = findNumberOfTransactions(datasource);
        assertEquals(originalNumberOfTransactions + 1, updatedNumberOfTransactions);
    }

    private void assertAccounts(DataSource datasource) throws Exception {
        try (Connection connection = datasource.getConnection()) {
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

    private void addTransaction(DataSource database, double amount) throws SQLException {
        try (Connection connection = database.getConnection()) {
            try(PreparedStatement statement = connection.prepareStatement("insert into transactions (account_id, store_id, transaction_amount) values (1, 1, ?)")) {
                statement.setDouble(1, amount);
                statement.executeUpdate();
            }
        }
    }

    private int findNumberOfTransactions(DataSource database) throws SQLException {
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
