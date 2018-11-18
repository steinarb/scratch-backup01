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
package no.bang.priv.handlereg.web.security;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.ops4j.pax.jdbc.derby.impl.DerbyDataSourceFactory;
import org.osgi.service.jdbc.DataSourceFactory;

import no.bang.priv.handlereg.db.derby.test.HandleregDerbyTestDatabase;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class HandleregShiroFilterTest {

    private static MockLogService logservice;
    private static HandleregDerbyTestDatabase database;

    @BeforeAll
    static void beforeAll() {
        DataSourceFactory derbyDataSourceFactory = new DerbyDataSourceFactory();
        logservice = new MockLogService();
        database = new HandleregDerbyTestDatabase();
        database.setLogService(logservice);
        database.setDataSourceFactory(derbyDataSourceFactory);
        database.activate();
    }

    @Test
    void testAuthenticate() {
        HandleregShiroFilter filter = new HandleregShiroFilter();
        filter.setDatabase(database);
        filter.activate();
        WebSecurityManager securitymanager = filter.getSecurityManager();
        AuthenticationToken token = new UsernamePasswordToken("jad", "1ad".toCharArray());
        AuthenticationInfo info = securitymanager.authenticate(token);
        assertEquals(1, info.getPrincipals().asList().size());
    }
}
