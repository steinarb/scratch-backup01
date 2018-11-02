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
    void testCreateAndFindDataInAllTables() throws Exception {
        DataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        MockLogService logservice = new MockLogService();
        HandleregDerbyTestDatabase database = new HandleregDerbyTestDatabase();
        database.setLogService(logservice);
        database.setDataSourceFactory(dataSourceFactory);
        database.activate();
        addUsers(database);
        assertUsers(database);

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

        // Replace the connection with a connection that will fail when
        // releasing the lock
        Connection connection = mock(Connection.class);
        database.connect = connection;

        boolean result = database.forceReleaseLiquibaseLock();
        assertFalse(result);
    }

    private void addUsers(HandleregDerbyTestDatabase database) throws Exception {
        addUser(database, "admin", "admin@gmail.com", "Admin", "Istrator", "pepper", "salt");
    }

    private void assertUsers(HandleregDerbyTestDatabase database) throws Exception {
        try(PreparedStatement statement = database.prepareStatement("select * from users join password on users.user_id=password.user_id")) {
            ResultSet results = database.query(statement);
            assertUser(results, "admin", "admin@gmail.com", "Admin", "Istrator", "pepper", "salt");
        }
    }

    private void addUser(HandleregDerbyTestDatabase database, String username, String email, String firstname, String lastname, String password, String salt) throws Exception {
        String userSql = "insert into users (username, email, firstname, lastname) values (?, ?, ?, ?)";
        try(PreparedStatement statement = database.prepareStatement(userSql)) {
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, firstname);
            statement.setString(4, lastname);
            database.update(statement);
        }

        int userid = -1;
        try(PreparedStatement queryForUserid = database.prepareStatement("select user_id from users where username=?")) {
            queryForUserid.setString(1, username);
            ResultSet results = database.query(queryForUserid);
            results.next();
            userid = results.getInt(1);
        }

        String passwordSql = "insert into password (user_id, username, password, salt) values (?, ?, ?, ?)";
        try(PreparedStatement statement = database.prepareStatement(passwordSql)) {
            statement.setInt(1, userid);
            statement.setString(2, username);
            statement.setString(3, password);
            statement.setString(4, salt);
            database.update(statement);
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

}
