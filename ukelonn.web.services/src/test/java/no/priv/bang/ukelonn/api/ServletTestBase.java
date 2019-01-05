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
package no.priv.bang.ukelonn.api;

import static no.priv.bang.ukelonn.testutils.TestUtils.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockrunner.mock.web.MockHttpServletRequest;

import no.priv.bang.ukelonn.api.beans.LoginCredentials;

public class ServletTestBase {
    public static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public ServletTestBase() {
        super();
    }

    protected WebSubject createSubjectAndBindItToThread(HttpServletRequest request, HttpServletResponse response) {
        WebSubject subject = new WebSubject.Builder(getSecurityManager(), request, response).buildWebSubject();
        ThreadContext.bind(subject);
        return subject;
    }

    protected WebSubject createSubjectWithNullPrincipalAndBindItToThread() {
        WebSubject subject = mock(WebSubject.class);
        ThreadContext.bind(subject);
        return subject;
    }

    protected void loginUser(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        WebSubject subject = createSubjectAndBindItToThread(request, response);
        UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), true);
        subject.login(token);
    }

    protected HttpServletRequest buildLoginRequest(LoginCredentials credentials) throws JsonProcessingException, IOException {
        String credentialsAsJson = ServletTestBase.mapper.writeValueAsString(credentials);
        return buildRequestFromStringBody("http://localhost:8181/ukelonn/api/login", credentialsAsJson);
    }

    protected HttpServletRequest buildGetRequest() throws IOException {
        HttpSession session = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getProtocol()).thenReturn("HTTP/1.1");
        when(request.getMethod()).thenReturn("GET");
        when(request.getContextPath()).thenReturn("/ukelonn");
        when(request.getServletPath()).thenReturn("/api");
        when(request.getHeaderNames()).thenReturn(Collections.enumeration(Collections.emptyList()));
        when(request.getSession()).thenReturn(session);
        return request;
    }

    protected MockHttpServletRequest buildRequestFromStringBody(String url, String textToSendAsBody) throws IOException {
        HttpSession session = mock(HttpSession.class);
        return MockHttpServletRequest.postJsonRequest(URI.create(url))
            .setBodyContent(textToSendAsBody)
            .setContextPath("/ukelonn")
            .setServletPath("/api")
            .setSession(session);
    }

}
