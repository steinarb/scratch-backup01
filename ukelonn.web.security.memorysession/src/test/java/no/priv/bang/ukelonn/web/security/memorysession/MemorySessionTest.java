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
package no.priv.bang.ukelonn.web.security.memorysession;

import static no.priv.bang.ukelonn.testutils.TestUtils.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import no.priv.bang.ukelonn.UkelonnDatabase;

public class MemorySessionTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void setupForAllTests() {
        setupFakeOsgiServices();
    }

}
