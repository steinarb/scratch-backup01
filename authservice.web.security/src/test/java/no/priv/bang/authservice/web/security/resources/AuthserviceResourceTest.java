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
package no.priv.bang.authservice.web.security.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Collectors;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;

import no.priv.bang.authservice.web.security.ShiroTestBase;

class AuthserviceResourceTest extends ShiroTestBase {

    @BeforeClass()
    static void setup() throws Exception {
        setupBase();
    }

    @Test
    void testGetIndex() {
        AuthserviceResource resource = new AuthserviceResource();
        InputStream htmlfile = resource.getIndex();
        String html = new BufferedReader(new InputStreamReader(htmlfile)).lines().collect(Collectors.joining("+n"));
        assertThat(html).startsWith("<html");
    }

    @Test
    void testGetLogin() {
        AuthserviceResource resource = new AuthserviceResource();
        InputStream htmlfile = resource.getLogin();
        String html = new BufferedReader(new InputStreamReader(htmlfile)).lines().collect(Collectors.joining("+n"));
        assertThat(html).startsWith("<html");
    }

    @Test
    void testPostLogin() {
        MockHttpServletRequest dummyrequest = new MockHttpServletRequest();
        MockHttpServletResponse dummyresponse = new MockHttpServletResponse();
        createSubjectAndBindItToThread(dummyrequest, dummyresponse);
        AuthserviceResource resource = new AuthserviceResource();
        String username = "admin";
        String password = "admin";
        Response response = resource.postLogin(username, password);
        assertEquals(302, response.getStatus());
        assertEquals("../..", response.getLocation().toString());
    }

    @Test
    void testCheckLogin() {
        MockHttpServletRequest dummyrequest = new MockHttpServletRequest();
        MockHttpServletResponse dummyresponse = new MockHttpServletResponse();
        createSubjectAndBindItToThread(dummyrequest, dummyresponse);
        AuthserviceResource resource = new AuthserviceResource();

        // Log a user in
        String username = "admin";
        String password = "admin";
        resource.postLogin(username, password);

        Response response = resource.checkLogin();
        assertEquals(200, response.getStatus());
    }

    @Test
    void testCheckLoginNotLoggedIn() {
        MockHttpServletRequest dummyrequest = new MockHttpServletRequest();
        MockHttpServletResponse dummyresponse = new MockHttpServletResponse();
        createSubjectAndBindItToThread(dummyrequest, dummyresponse);
        AuthserviceResource resource = new AuthserviceResource();

        Response response = resource.checkLogin();
        assertEquals(401, response.getStatus());
    }

    @Test
    void testCheckLoginNoSubject() {
        Subject nosubject = null;
        ThreadContext.bind(nosubject);
        AuthserviceResource resource = new AuthserviceResource();

        Response response = resource.checkLogin();
        assertEquals(401, response.getStatus());
    }

    @Test
    void testFindRedirectLocation() {
        AuthserviceResource resource = new AuthserviceResource();
        URI locationWithoutOriginalUri = resource.findRedirectLocation();
        assertEquals(URI.create("../.."), locationWithoutOriginalUri);

        HttpHeaders httpHeadersWithoutOriginalUri = mock(HttpHeaders.class);
        resource.httpHeaders = httpHeadersWithoutOriginalUri;
        URI locationAlsoWithoutOriginalUri = resource.findRedirectLocation();
        assertEquals(URI.create("../.."), locationAlsoWithoutOriginalUri);

        HttpHeaders httpHeadersWithOriginalUri = mock(HttpHeaders.class);
        when(httpHeadersWithOriginalUri.getHeaderString(anyString())).thenReturn("http://lorenzo.hjemme.lan/authservice/login");
        resource.httpHeaders = httpHeadersWithOriginalUri;
        URI locationWithOriginalUri = resource.findRedirectLocation();
        assertEquals(URI.create("http://lorenzo.hjemme.lan/"), locationWithOriginalUri);
    }

}
