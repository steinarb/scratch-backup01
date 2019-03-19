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
package no.priv.bang.authservice.definitions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;

class DelegateLoginServletTest {

    @Test
    void testAllMethodsWithThrow() throws Exception {
        AuthserviceLoginServlet logindelegate = mock(AuthserviceLoginServlet.class);
        doThrow(ServletException.class).when(logindelegate).init(any());
        ServletConfig config = mock(ServletConfig.class);
        when(logindelegate.getServletConfig()).thenReturn(config);
        doThrow(ServletException.class).when(logindelegate).service(any(), any());
        String info = "servlet info";
        when(logindelegate.getServletInfo()).thenReturn(info);
        doThrow(AuthserviceException.class).when(logindelegate).destroy();

        DelegateLoginServlet servlet = new DelegateLoginServlet();
        servlet.setDelegate(logindelegate);

        // Test all methods of the delegate
        assertThrows(ServletException.class, () -> {
                servlet.init(null);
            });
        assertEquals(config, servlet.getServletConfig());
        assertThrows(ServletException.class, () -> {
                servlet.service(null, null);
            });
        assertEquals(info, servlet.getServletInfo());
        assertThrows(AuthserviceException.class, () -> {
                servlet.destroy();
            });
    }

    @Test
    void testNoThrow() throws Exception {
        AuthserviceLoginServlet logindelegate = mock(AuthserviceLoginServlet.class);
        ServletConfig config = mock(ServletConfig.class);
        when(logindelegate.getServletConfig()).thenReturn(config);
        String info = "servlet info";
        when(logindelegate.getServletInfo()).thenReturn(info);

        DelegateLoginServlet servlet = new DelegateLoginServlet();
        servlet.setDelegate(logindelegate);

        // Test all methods of the delegate
        servlet.init(null);
        assertEquals(config, servlet.getServletConfig());
        servlet.service(null, null);
        servlet.destroy();
        assertEquals(info, servlet.getServletInfo());
    }

}
