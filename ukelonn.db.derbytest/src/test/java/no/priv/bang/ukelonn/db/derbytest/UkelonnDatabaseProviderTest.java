/*
 * Copyright 2016-2018 Steinar Bang
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
package no.priv.bang.ukelonn.db.derbytest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.internal.util.reflection.Whitebox.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;
import javax.sql.PooledConnection;

import org.apache.derby.jdbc.ClientConnectionPoolDataSource;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.ByteSource.Util;
import org.assertj.core.api.SoftAssertions;
import org.junit.Ignore;
import org.junit.Test;
import org.ops4j.pax.jdbc.derby.impl.DerbyDataSourceFactory;
import org.osgi.service.jdbc.DataSourceFactory;
import liquibase.Liquibase;
import liquibase.changelog.RanChangeSet;
import liquibase.database.DatabaseConnection;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;
import no.priv.bang.osgiservice.database.DatabaseServiceException;
import no.priv.bang.ukelonn.db.liquibase.UkelonnLiquibase;

public class UkelonnDatabaseProviderTest {

    @Test
    public void testGetName() {
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();

        String databaseName = provider.getName();
        assertEquals("Ukelonn Derby test database", databaseName);
    }

    @Test
    public void testThatActivatorCreatesDatabase() throws SQLException, DatabaseException {
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();
        provider.setLogService(new MockLogService());
        DerbyDataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        provider.setDataSourceFactory(dataSourceFactory);
        provider.activate(); // Create the database

        // test getting the datasource
        DataSource datasource = provider.getDatasource();
        assertNotNull(datasource);

        try(Connection connect = provider.getConnection()) {
            assertNotNull(connect);
        }

        // Successful force release liquibase lock
        provider.forceReleaseLocks();

        // Test the database by making a query using a view
        try(Connection connection = provider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from accounts_view where username=?");
            statement.setString(1, "jad");
            ResultSet onAccount = statement.executeQuery();
            assertNotNull(onAccount);
            assertTrue(onAccount.next());
            int account_id = onAccount.getInt("account_id");
            String username = onAccount.getString("username");
            float balance = onAccount.getFloat("balance");
            assertEquals(4, account_id);
            assertEquals("jad", username);
            assertThat(balance).isGreaterThan(0);
        }

        // Verify that the schema changeset as well as all of the test data change sets has been run
        List<RanChangeSet> ranChangeSets = provider.getChangeLogHistory();
        assertEquals(47, ranChangeSets.size());
    }

    @Test
    public void testInsert() throws SQLException {
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();
        provider.setLogService(new MockLogService());
        DerbyDataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        provider.setDataSourceFactory(dataSourceFactory);
        provider.activate(); // Create the database

        // Verify that the user isn't present
        try(Connection connection = provider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("select * from users where username=?");
            statement.setString(1, "jjd");
            ResultSet userJjdBeforeInsert = statement.executeQuery();
            int numberOfUserJjdBeforeInsert = 0;
            while (userJjdBeforeInsert.next()) { ++numberOfUserJjdBeforeInsert; }
            assertEquals(0, numberOfUserJjdBeforeInsert);

            PreparedStatement updateStatement = connection.prepareStatement("insert into users (username,password,password_salt,email,firstname,lastname) values (?, ?, ?, ?, ?, ?)");
            updateStatement.setString(1, "jjd");
            updateStatement.setString(2, "sU4vKCNpoS6AuWAzZhkNk7BdXSNkW2tmOP53nfotDjE=");
            updateStatement.setString(3, "9SFDvohxZkZ9eWHiSEoMDw==");
            updateStatement.setString(4, "jjd@gmail.com");
            updateStatement.setString(5, "James");
            updateStatement.setString(6, "Davies");
            int count = updateStatement.executeUpdate();
            assertEquals(1, count);

            // Verify that the user is now present
            PreparedStatement statement2 = connection.prepareStatement("select * from users where username=?");
            statement2.setString(1, "jjd");
            ResultSet userJjd = statement2.executeQuery();
            int numberOfUserJjd = 0;
            while (userJjd.next()) { ++numberOfUserJjd; }
            assertEquals(1, numberOfUserJjd);
        }
    }

    @Test(expected=SQLSyntaxErrorException.class)
    public void testBadSql() throws Exception {
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();
        provider.setLogService(new MockLogService());
        DerbyDataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        provider.setDataSourceFactory(dataSourceFactory);
        provider.activate(); // Create the database

        // A bad select returns a null instead of a prepared statement
        try(Connection connection = provider.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("zelect * from uzers");
            assertNull(statement);
        }
    }

    @Test
    public void testFailToCreateDataSource() throws SQLException {
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();
        MockLogService logservice = new MockLogService();
        provider.setLogService(logservice);

        // Verify precondition (nothing logged)
        assertEquals(0, logservice.getLogmessages().size());

        // Run method under test
        provider.createDatasource();

        // Verify that an error message has been logged
        assertEquals(1, logservice.getLogmessages().size());
    }

    @SuppressWarnings("unchecked")
    @Test(expected=DatabaseServiceException.class)
    public void testFailToCreateDatabaseConnection() throws SQLException {
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();
        provider.setLogService(new MockLogService());
        DataSourceFactory dataSourceFactory = mock(DataSourceFactory.class);
        when(dataSourceFactory.createConnectionPoolDataSource(any(Properties.class))).thenThrow(SQLException.class);
        provider.setDataSourceFactory(dataSourceFactory); // Test what happens with failing datasource injection
        provider.activate(); // Create the database

        try(Connection connection = provider.getConnection()) {
            assertNull(connection);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFailToInsertMockData() throws SQLException {
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();
        provider.setLogService(new MockLogService());
        DataSourceFactory dataSourceFactory = mock(DataSourceFactory.class);
        PooledConnection pooledconnection = mock(PooledConnection.class);
        when(pooledconnection.getConnection()).thenThrow(SQLException.class);
        when(dataSourceFactory.createConnectionPoolDataSource(any(Properties.class))).thenThrow(SQLException.class);

        // Bypass injection to skip schema creation and be able to test
        // database failure on data insertion
        setInternalState(provider, "dataSourceFactory", dataSourceFactory);

        boolean result = provider.insertMockData();
        assertFalse(result);
    }

    @Test
    public void testRollbackMockData() throws Exception {
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();
        provider.setLogService(new MockLogService());
        DerbyDataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        provider.setDataSourceFactory(dataSourceFactory);
        provider.activate(); // Create the database

        // Check that database has the mock data in place
        SoftAssertions expectedStatusBeforeRollback = new SoftAssertions();
        int numberOfTransactionTypesBeforeRollback = findTheNumberOfRowsInTable(provider, "transaction_types");
        expectedStatusBeforeRollback.assertThat(numberOfTransactionTypesBeforeRollback).isGreaterThan(0);
        int numberOfUsersBeforeRollback = findTheNumberOfRowsInTable(provider, "users");
        expectedStatusBeforeRollback.assertThat(numberOfUsersBeforeRollback).isGreaterThan(0);
        int numberOfAccountsBeforeRollback = findTheNumberOfRowsInTable(provider, "accounts");
        expectedStatusBeforeRollback.assertThat(numberOfAccountsBeforeRollback).isGreaterThan(0);
        int numberOfTransactionsBeforeRollback = findTheNumberOfRowsInTable(provider, "transactions");
        expectedStatusBeforeRollback.assertThat(numberOfTransactionsBeforeRollback).isGreaterThan(0);
        expectedStatusBeforeRollback.assertAll();

        int sizeOfDbchangelogBeforeRollback = findTheNumberOfRowsInTable(provider, "databasechangelog");

        // Do the rollback
        boolean rollbackSuccessful = provider.rollbackMockData();
        assertTrue(rollbackSuccessful);

        int sizeOfDbchangelogAfterRollback = findTheNumberOfRowsInTable(provider, "databasechangelog");
        assertThat(sizeOfDbchangelogAfterRollback).isLessThan(sizeOfDbchangelogBeforeRollback);

        // Verify that the database tables are empty
        SoftAssertions expectedStatusAfterRollback = new SoftAssertions();
        int numberOfTransactionTypesAfterRollback = findTheNumberOfRowsInTable(provider, "transaction_types");
        expectedStatusAfterRollback.assertThat(numberOfTransactionTypesAfterRollback).isEqualTo(0);
        int numberOfUsersAfterRollback = findTheNumberOfRowsInTable(provider, "users");
        expectedStatusAfterRollback.assertThat(numberOfUsersAfterRollback).isEqualTo(0);
        int numberOfAccountsAfterRollback = findTheNumberOfRowsInTable(provider, "accounts");
        expectedStatusAfterRollback.assertThat(numberOfAccountsAfterRollback).isEqualTo(0);
        int numberOfTransactionsAfterRollback = findTheNumberOfRowsInTable(provider, "transactions");
        expectedStatusAfterRollback.assertThat(numberOfTransactionsAfterRollback).isEqualTo(0);
        expectedStatusAfterRollback.assertAll();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testFailToRollbackMockData() throws Exception {
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();
        provider.setLogService(new MockLogService());
        DataSourceFactory dataSourceFactory = mock(DataSourceFactory.class);
        PooledConnection pooledconnection = mock(PooledConnection.class);
        when(pooledconnection.getConnection()).thenThrow(SQLException.class);
        when(dataSourceFactory.createConnectionPoolDataSource(any(Properties.class))).thenThrow(SQLException.class);

        boolean rollbackSuccessful = provider.rollbackMockData();
        assertFalse(rollbackSuccessful);
    }

    @Test
    public void testFailToForceLock() {
        MockLogService logservice = new MockLogService();
        UkelonnDatabaseProvider provider = new UkelonnDatabaseProvider();
        provider.setLogService(logservice);

        // Check precondition that nothing has been logged
        assertEquals(0, logservice.getLogmessages().size());

        // Run the method under test
        provider.forceReleaseLocks();

        // Check that an error message has been logged
        assertEquals(1, logservice.getLogmessages().size());
    }

    /**
     * Not a real unit test, just a way to hash cleartext passwords for
     * the test database and generate salt.
     */
    @Test
    public void testCreateHashedPasswords() {
        String[] usernames = { "on", "kn", "jad", "jod" };
        String[] unhashedPasswords = { "ola12", "KaRi", "1ad", "johnnyBoi" };
        RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        System.out.println("username, password, salt");
        for (int i=0; i<usernames.length; ++i) {
            // First hash the password
            String username = usernames[i];
            String password = unhashedPasswords[i];
            String salt = randomNumberGenerator.nextBytes().toBase64();
            Object decodedSaltUsedWhenHashing = Util.bytes(Base64.getDecoder().decode(salt));
            String hashedPassword = new Sha256Hash(password, decodedSaltUsedWhenHashing, 1024).toBase64();

            // Check the cleartext password against the hashed password
            UsernamePasswordToken usenamePasswordToken = new UsernamePasswordToken(username, password.toCharArray());
            SimpleAuthenticationInfo saltedAuthenticationInfo = createAuthenticationInfo(usernames[i], hashedPassword, salt);
            CredentialsMatcher credentialsMatcher = createSha256HashMatcher(1024);
            assertTrue(credentialsMatcher.doCredentialsMatch(usenamePasswordToken, saltedAuthenticationInfo));

            // Print out the username, hashed password, and salt
            System.out.println(String.format("'%s', '%s', '%s'", username, hashedPassword, salt));
        }
    }

    /**
     * Not an actual unit test.
     *
     * This test is a convenient way to populate a derby network server
     * running on localhost, with the ukelonn schema and test data, using
     * liquibase.
     *
     * To use this test:
     *  1. Start a derby network server
     *  2. Remove the @Ignore annotation of this test
     *  3. Run the test
     *
     * After this test has been run the derby network server will have
     * a database named "ukelonn" containing the ukelonn schema
     * and the test data used by unit tests.
     *
     * @throws SQLException
     * @throws LiquibaseException
     */
    @Ignore("Not an actual unit test. This test is a convenient way to populate a derby network server running on localhost, with the ukelonn schema and test data, using liquibase.")
    @Test
    public void addUkelonnSchemaAndDataToDerbyServer() throws SQLException, LiquibaseException { // NOSONAR This isn't an actual test, see the comments
        boolean createUkelonnDatabase = true;
        ClientConnectionPoolDataSource dataSource = new ClientConnectionPoolDataSource();
        dataSource.setServerName("localhost");
        dataSource.setDatabaseName("ukelonn");
        dataSource.setPortNumber(1527);
        if (createUkelonnDatabase) {
            dataSource.setCreateDatabase("create");
        }

        Connection connect = dataSource.getConnection();
        UkelonnLiquibase liquibase = new UkelonnLiquibase();
        liquibase.createInitialSchema(connect);
        DatabaseConnection databaseConnection = new JdbcConnection(connect);
        ClassLoaderResourceAccessor classLoaderResourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
        Liquibase liquibase2 = new Liquibase("sql/data/db-changelog.xml", classLoaderResourceAccessor, databaseConnection);
        liquibase2.update("");
        liquibase.updateSchema(connect);
    }

    private CredentialsMatcher createSha256HashMatcher(int iterations) {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME);
        credentialsMatcher.setHashIterations(iterations);
        return credentialsMatcher;
    }

    private SimpleAuthenticationInfo createAuthenticationInfo(String principal, String hashedPassword, String salt) {
        Object decodedPassword = Sha256Hash.fromBase64String(hashedPassword);
        ByteSource decodedSalt = Util.bytes(Base64.getDecoder().decode(salt));
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(principal, decodedPassword, decodedSalt, "ukelonn");
        return authenticationInfo;
    }

    private int findTheNumberOfRowsInTable(UkelonnDatabaseProvider provider, String tableName) throws Exception {
        String selectAllRowsStatement = String.format("select * from %s", tableName);
        try(Connection connection = provider.getConnection()) {
            try(PreparedStatement selectAllRowsInTable = connection.prepareStatement(selectAllRowsStatement)) {
                ResultSet userResults = selectAllRowsInTable.executeQuery();
                int numberOfUsers = countResults(userResults);
                return numberOfUsers;
            }
        }
    }

    private int countResults(ResultSet results) throws Exception {
        int numberOfResultsInResultSet = 0;
        while(results.next()) {
            ++numberOfResultsInResultSet;
        }

        return numberOfResultsInResultSet;
    }

}
