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
package no.bang.priv.handlereg.web.api;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ServerProperties;
import org.junit.jupiter.api.Test;
import org.osgi.service.log.LogService;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;

import no.bang.priv.handlereg.services.Butikk;
import no.bang.priv.handlereg.services.HandleregService;
import no.bang.priv.handlereg.services.NyHandling;
import no.bang.priv.handlereg.services.Oversikt;
import no.bang.priv.handlereg.services.Transaction;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class HandleregWebApiTest extends ShiroTestBase {
    public static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    @Test
    void testGetOversikt() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        Oversikt jdOversikt = new Oversikt(1, "jd", "johndoe@gmail.com", "John", "Doe", 1500);
        when(handlereg.finnOversikt("jd")).thenReturn(jdOversikt);
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildGetUrl("/oversikt");
        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testGetHandlinger() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.findLastTransactions(eq(1))).thenReturn(Arrays.asList(new Transaction()));
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildGetUrl("/handlinger/1");
        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testPostNyhandlinger() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        Oversikt oversikt = new Oversikt(1, "jd", "johndoe@gmail.com", "John", "Doe", 500);
        when(handlereg.registrerHandling(any())).thenReturn(oversikt);
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildPostUrl("/nyhandling");
        NyHandling handling = new NyHandling("jd", 1, 1, 510, new Date());
        String postBody = mapper.writeValueAsString(handling);
        request.setBodyContent(postBody);

        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testGetButikker() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.finnButikker()).thenReturn(Arrays.asList(new Butikk()));
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildGetUrl("/butikker");

        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testLeggTilButikk() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.finnButikker()).thenReturn(Arrays.asList(new Butikk()));
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildPostUrl("/nybutikk");
        Butikk butikk = new Butikk("Ny butikk");
        String postBody = mapper.writeValueAsString(butikk);
        request.setBodyContent(postBody);

        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
    }

    private MockHttpServletRequest buildGetUrl(String resource) {
        MockHttpServletRequest request = buildRequest(resource);
        request.setMethod("GET");
        return request;
    }

    private MockHttpServletRequest buildPostUrl(String resource) {
        String contenttype = MediaType.APPLICATION_JSON;
        MockHttpServletRequest request = buildRequest(resource);
        request.setMethod("POST");
        request.setContentType(contenttype);
        request.addHeader("Content-Type", contenttype);
        return request;
    }

    private MockHttpServletRequest buildRequest(String resource) {
        MockHttpSession session = new MockHttpSession();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setProtocol("HTTP/1.1");
        request.setRequestURL("http://localhost:8181/handlereg/api" + resource);
        request.setRequestURI("/handlereg/api" + resource);
        request.setContextPath("/handlereg");
        request.setServletPath("/api");
        request.setSession(session);
        return request;
    }

    private HandleregWebApi simulateDSComponentActivationAndWebWhiteboardConfiguration(HandleregService handlereg, LogService logservice) throws Exception {
        HandleregWebApi servlet = new HandleregWebApi();
        servlet.setLogservice(logservice);
        servlet.setHandleregService(handlereg);
        servlet.activate();
        ServletConfig config = createServletConfigWithApplicationAndPackagenameForJerseyResources();
        servlet.init(config);
        return servlet;
    }

    private ServletConfig createServletConfigWithApplicationAndPackagenameForJerseyResources() {
        ServletConfig config = mock(ServletConfig.class);
        when(config.getInitParameterNames()).thenReturn(Collections.enumeration(Arrays.asList(ServerProperties.PROVIDER_PACKAGES)));
        when(config.getInitParameter(eq(ServerProperties.PROVIDER_PACKAGES))).thenReturn("no.bang.priv.handlereg.web.api.resources");
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getContextPath()).thenReturn("/handlereg");
        when(config.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttributeNames()).thenReturn(Collections.emptyEnumeration());
        return config;
    }
}
