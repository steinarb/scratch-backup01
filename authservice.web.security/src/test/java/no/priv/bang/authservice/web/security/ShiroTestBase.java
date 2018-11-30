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
package no.priv.bang.authservice.web.security;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.jupiter.api.BeforeAll;
import org.ops4j.pax.jdbc.derby.impl.DerbyDataSourceFactory;

import liquibase.exception.LiquibaseException;
import no.priv.bang.authservice.db.derby.test.DerbyTestDatabase;
import no.priv.bang.authservice.web.security.dbrealm.AuthserviceDbRealm;
import no.priv.bang.authservice.web.security.memorysession.MemorySession;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

public class ShiroTestBase {

    private static AuthserviceShiroFilter filter;

    @BeforeAll
    public static void setupBase() throws SQLException, LiquibaseException {
        DerbyDataSourceFactory dataSourceFactory = new DerbyDataSourceFactory();
        MockLogService logservice = new MockLogService();
        DerbyTestDatabase database = new DerbyTestDatabase();
        database.setLogservice(logservice);
        database.setDataSourceFactory(dataSourceFactory);
        database.activate();
        AuthserviceDbRealm realm = new AuthserviceDbRealm();
        realm.setDatabaseService(database);
        realm.activate();
        MemorySession session = new MemorySession();
        session.activate();
        ServletContext context = mock(ServletContext.class);
        when(context.getContextPath()).thenReturn("/authservice");
        filter = new AuthserviceShiroFilter();
        filter.setServletContext(context);
        filter.setRealm(realm);
        filter.setSession(session);
        filter.activate();
    }

    public ShiroTestBase() {
        super();
    }

    protected WebSubject createSubjectAndBindItToThread(HttpServletRequest request, HttpServletResponse response) {
        WebSubject subject = new WebSubject.Builder(filter.getSecurityManager(), request, response).buildWebSubject();
        ThreadContext.bind(subject);
        return subject;
    }

}