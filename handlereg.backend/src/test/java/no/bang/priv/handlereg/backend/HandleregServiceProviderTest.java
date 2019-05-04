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
package no.bang.priv.handlereg.backend;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ops4j.pax.jdbc.derby.impl.DerbyDataSourceFactory;
import org.osgi.service.jdbc.DataSourceFactory;
import no.bang.priv.handlereg.db.derby.test.HandleregDerbyTestDatabase;
import no.bang.priv.handlereg.services.Butikk;
import no.bang.priv.handlereg.services.HandleregDatabase;
import no.bang.priv.handlereg.services.HandleregException;
import no.bang.priv.handlereg.services.NyHandling;
import no.bang.priv.handlereg.services.Oversikt;
import no.bang.priv.handlereg.services.Transaction;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;
import no.priv.bang.osgiservice.users.User;
import no.priv.bang.osgiservice.users.UserManagementService;

class HandleregServiceProviderTest {
    static DataSourceFactory derbyDataSourceFactory = new DerbyDataSourceFactory();
    private static HandleregDerbyTestDatabase database;

    @BeforeAll
    static void commonSetupForAllTests() {
        database = new HandleregDerbyTestDatabase();
        database.setDataSourceFactory(derbyDataSourceFactory);
        database.activate();
    }

    @Test
    void testHentOversikt() {
        MockLogService logservice = new MockLogService();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        when(useradmin.getUser(anyString())).thenReturn(new User(1, "jd", "jd@gmail.com", "John", "Doe"));
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(database);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        Oversikt jd = handlereg.finnOversikt("jd");
        assertEquals(1, jd.getUserId());
        assertEquals("jd", jd.getBrukernavn());
        assertEquals("John", jd.getFornavn());
        assertEquals("Doe", jd.getEtternavn());
        assertThat(jd.getBalanse()).isGreaterThan(0.0);
    }

    @Test
    void testHentOversiktMedDbFeil() throws Exception {
        MockLogService logservice = new MockLogService();
        HandleregDatabase mockdb = createMockDbWithResultSetThatThrowsExceptionWhenIterated();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(mockdb);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        assertEquals(0, logservice.getLogmessages().size());
        assertThrows(HandleregException.class, () -> {
                Oversikt jd = handlereg.finnOversikt("jd");
                assertNotNull(jd, "Should never get here");
            });
        assertEquals(1, logservice.getLogmessages().size());
    }

    @Test
    void testHentOversiktMedTomtResultat() throws Exception {
        MockLogService logservice = new MockLogService();
        HandleregDatabase mockdb = createMockDbWithEmptyResultset();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(mockdb);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        assertEquals(0, logservice.getLogmessages().size());
        Oversikt jd = handlereg.finnOversikt("jd");
        assertNull(jd);
        assertEquals(0, logservice.getLogmessages().size());
    }

    @Test
    void testHentHandlinger() {
        MockLogService logservice = new MockLogService();
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        UserManagementService useradmin = mock(UserManagementService.class);
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(database);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        List<Transaction> handlinger = handlereg.findLastTransactions(1);
        assertEquals(10, handlinger.size());
    }

    @Test
    void testHentHandlingerMedDbFeil() throws Exception {
        MockLogService logservice = new MockLogService();
        HandleregDatabase mockdb = createMockDbWithResultSetThatThrowsExceptionWhenIterated();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(mockdb);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        assertEquals(0, logservice.getLogmessages().size());
        assertThrows(HandleregException.class, () -> {
                List<Transaction> handlinger = handlereg.findLastTransactions(1);
                assertNull(handlinger, "Should never get here");
            });
        assertEquals(1, logservice.getLogmessages().size());
    }

    @Test
    void testRegistrerHandling() {
        MockLogService logservice = new MockLogService();
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        UserManagementService useradmin = mock(UserManagementService.class);
        when(useradmin.getUser(anyString())).thenReturn(new User(1, "jd", "jd@gmail.com", "John", "Doe"));
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(database);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        Oversikt originalOversikt = handlereg.finnOversikt("jd");
        double originalBalanse = originalOversikt.getBalanse();
        double nyttBelop = 510;
        Date now = new Date();
        NyHandling nyHandling = new NyHandling("jd", 1, 1, nyttBelop, now);
        Oversikt nyOversikt = handlereg.registrerHandling(nyHandling);
        assertThat(nyOversikt.getBalanse()).isEqualTo(originalBalanse + nyttBelop);
    }

    @Test
    void testRegistrerHandlingNoDate() {
        MockLogService logservice = new MockLogService();
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        UserManagementService useradmin = mock(UserManagementService.class);
        when(useradmin.getUser(anyString())).thenReturn(new User(1, "jd", "jd@gmail.com", "John", "Doe"));
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(database);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        Oversikt originalOversikt = handlereg.finnOversikt("jd");
        double originalBalanse = originalOversikt.getBalanse();
        double nyttBelop = 510;
        NyHandling nyHandling = new NyHandling("jd", 1, 1, nyttBelop, null);
        Oversikt nyOversikt = handlereg.registrerHandling(nyHandling);
        assertThat(nyOversikt.getBalanse()).isEqualTo(originalBalanse + nyttBelop);
    }

    @Test
    void testRegistrerHandlingMedDbFeil() throws Exception {
        MockLogService logservice = new MockLogService();
        HandleregDatabase mockdb = createMockDbThrowingException();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(mockdb);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        double nyttBelop = 510;
        Date now = new Date();
        NyHandling nyHandling = new NyHandling("jd", 1, 1, nyttBelop, now);
        assertThrows(HandleregException.class, () -> {
                Oversikt nyOversikt = handlereg.registrerHandling(nyHandling);
                assertNull(nyOversikt, "Should never get here");
            });
    }

    @Test
    void testFinnButikker() {
        MockLogService logservice = new MockLogService();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(database);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        List<Butikk> butikker = handlereg.finnButikker();
        assertEquals(135, butikker.size());
    }

    @Test
    void testFinnButikkerMedDbFeil() throws Exception {
        MockLogService logservice = new MockLogService();
        HandleregDatabase mockdb = createMockDbWithResultSetThatThrowsExceptionWhenIterated();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(mockdb);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        assertEquals(0, logservice.getLogmessages().size());
        assertThrows(HandleregException.class, () -> {
                List<Butikk> butikker = handlereg.finnButikker();
                assertNull(butikker, "Should never get here");
            });
        assertEquals(1, logservice.getLogmessages().size());
    }

    @Test
    void testLeggTilButikk() {
        MockLogService logservice = new MockLogService();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(database);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        List<Butikk> butikkerFoerOppdatering = handlereg.finnButikker();
        Butikk nybutikk = new Butikk("Spar fjellheimen");
        List<Butikk> butikker = handlereg.leggTilButikk(nybutikk);
        assertEquals(butikkerFoerOppdatering.size() + 1, butikker.size());
    }

    @Test
    void testLeggTilButikkMedDbFeilVedLagring() throws Exception {
        MockLogService logservice = new MockLogService();
        HandleregDatabase mockdb = createMockDbThrowingException();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(mockdb);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        Butikk nybutikk = new Butikk("Spar fjellheimen", 2, 1500);
        assertThrows(HandleregException.class, () -> {
                List<Butikk> butikker = handlereg.leggTilButikk(nybutikk);
                assertEquals(0, butikker.size());
            });
    }

    @Test
    void testFinnNesteLedigeRekkefolgeForGruppe() throws Exception {
        MockLogService logservice = new MockLogService();
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        UserManagementService useradmin = mock(UserManagementService.class);
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(database);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        int sisteLedigeForGruppe1 = finnSisteRekkefolgeForgruppe(1);
        int nesteLedigeRekkefolgeForGruppe1 = handlereg.finnNesteLedigeRekkefolgeForGruppe(1);
        assertEquals(sisteLedigeForGruppe1 + 10, nesteLedigeRekkefolgeForGruppe1);
        int sisteLedigeForGruppe2 = finnSisteRekkefolgeForgruppe(2);
        int nesteLedigeRekkefolgeForGruppe2 = handlereg.finnNesteLedigeRekkefolgeForGruppe(2);
        assertEquals(sisteLedigeForGruppe2 + 10, nesteLedigeRekkefolgeForGruppe2);
    }

    @Test
    void testFinnNesteLedigeRekkefolgeNaarDetIkkeErNoenTreff() throws Exception {
        MockLogService logservice = new MockLogService();
        HandleregDatabase mockdb = createMockDbWithEmptyResultset();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(mockdb);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        int nesteLedigeRekkefolge = handlereg.finnNesteLedigeRekkefolgeForGruppe(1);
        assertEquals(0, nesteLedigeRekkefolge);
    }

    @Test
    void testFinnNesteLedigeRekkefolgeNaarDetBlirKastetException() throws Exception {
        MockLogService logservice = new MockLogService();
        HandleregDatabase mockdb = createMockDbThrowingException();
        UserManagementService useradmin = mock(UserManagementService.class);
        HandleregServiceProvider handlereg = new HandleregServiceProvider();
        handlereg.setLogservice(logservice);
        handlereg.setDatabase(mockdb);
        handlereg.setUseradmin(useradmin);
        handlereg.activate();

        assertThrows(HandleregException.class, () -> {
                int nesteLedigeRekkefolge = handlereg.finnNesteLedigeRekkefolgeForGruppe(1);
                assertEquals(0, nesteLedigeRekkefolge, "Should never get here");
            });
    }

    private HandleregDatabase createMockDbWithEmptyResultset() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);
        when(results.next()).thenReturn(false);
        when(statement.executeQuery()).thenReturn(results);
        HandleregDatabase mockdb = mock(HandleregDatabase.class);
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(mockdb.getConnection()).thenReturn(connection);
        return mockdb;
    }

    @SuppressWarnings("unchecked")
    private HandleregDatabase createMockDbWithResultSetThatThrowsExceptionWhenIterated() throws SQLException {
        PreparedStatement statement = mock(PreparedStatement.class);
        ResultSet results = mock(ResultSet.class);
        when(results.next()).thenThrow(SQLException.class);
        when(statement.executeQuery()).thenReturn(results);
        HandleregDatabase mockdb = mock(HandleregDatabase.class);
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(anyString())).thenReturn(statement);
        when(mockdb.getConnection()).thenReturn(connection);
        return mockdb;
    }

    @SuppressWarnings("unchecked")
    private HandleregDatabase createMockDbThrowingException() throws SQLException {
        HandleregDatabase mockdb = mock(HandleregDatabase.class);
        Connection connection = mock(Connection.class);
        when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
        when(mockdb.getConnection()).thenReturn(connection);
        return mockdb;
    }

    private int finnSisteRekkefolgeForgruppe(int gruppe) throws Exception {
        try (Connection connection = database.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("select rekkefolge from stores where gruppe=? order by rekkefolge desc fetch next 1 rows only")) {
                statement.setInt(1, gruppe);
                try (ResultSet results = statement.executeQuery()) {
                    if (results.next()) {
                        return results.getInt(1);
                    }
                }
            }
        }

        return -1;
    }

}
