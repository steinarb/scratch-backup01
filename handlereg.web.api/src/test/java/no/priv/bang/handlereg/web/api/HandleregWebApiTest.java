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
package no.priv.bang.handlereg.web.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.ServerProperties;
import org.junit.jupiter.api.Test;
import org.osgi.service.log.LogService;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpServletResponse;
import com.mockrunner.mock.web.MockHttpSession;
import com.mockrunner.mock.web.MockServletOutputStream;

import no.priv.bang.handlereg.services.Butikk;
import no.priv.bang.handlereg.services.ButikkCount;
import no.priv.bang.handlereg.services.ButikkDate;
import no.priv.bang.handlereg.services.ButikkSum;
import no.priv.bang.handlereg.services.Credentials;
import no.priv.bang.handlereg.services.HandleregService;
import no.priv.bang.handlereg.services.NyHandling;
import no.priv.bang.handlereg.services.Oversikt;
import no.priv.bang.handlereg.services.SumYear;
import no.priv.bang.handlereg.services.SumYearMonth;
import no.priv.bang.handlereg.services.Transaction;
import no.priv.bang.handlereg.web.api.HandleregWebApi;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class HandleregWebApiTest extends ShiroTestBase {
    public static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .findAndRegisterModules();

    @Test
    void testLogin() throws Exception {
        String username = "jd";
        String password = "johnnyBoi";
        Credentials credentials = new Credentials(username, password);
        MockLogService logservice = new MockLogService();
        HandleregService handlereg = mock(HandleregService.class);
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg , logservice);
        createSubjectAndBindItToThread();
        MockHttpServletRequest request = buildPostUrl("/login");
        String postBody = mapper.writeValueAsString(credentials);
        request.setBodyContent(postBody);
        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.service(request, response);
        assertEquals(200, response.getStatus());

    }

    @Test
    void testLoginWrongPassword() throws Exception {
        String username = "jd";
        String password = "johnniBoi";
        Credentials credentials = new Credentials(username, password);
        MockLogService logservice = new MockLogService();
        HandleregService handlereg = mock(HandleregService.class);
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg , logservice);
        createSubjectAndBindItToThread();
        MockHttpServletRequest request = buildPostUrl("/login");
        String postBody = mapper.writeValueAsString(credentials);
        request.setBodyContent(postBody);
        MockHttpServletResponse response = new MockHttpServletResponse();

        servlet.service(request, response);
        assertEquals(200, response.getStatus());

    }

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

    @Test
    void testEndreButikk() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.endreButikk(any())).thenReturn(Arrays.asList(new Butikk()));
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildPostUrl("/endrebutikk");
        Butikk butikk = new Butikk("Ny butikk");
        String postBody = mapper.writeValueAsString(butikk);
        request.setBodyContent(postBody);

        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
    }

    @Test
    void testGetSumOverButikk() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.sumOverButikk()).thenReturn(Arrays.asList(new ButikkSum(new Butikk("Spar Fjellheimen"), 3345), new ButikkSum(new Butikk("Joker Nord"), 1234)));
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildGetUrl("/statistikk/sumbutikk");

        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
        List<ButikkSum> sumOverButikk = mapper.readValue(getBinaryContent(response), new TypeReference<List<ButikkSum>>() {});
        assertThat(sumOverButikk).isNotEmpty();
        assertEquals("Spar Fjellheimen", sumOverButikk.get(0).getButikk().getButikknavn());
    }

    @Test
    void testAntallHandlingerIButikk() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.antallHandlingerIButikk()).thenReturn(Arrays.asList(new ButikkCount(new Butikk("Spar Fjellheimen"), 3345), new ButikkCount(new Butikk("Joker Nord"), 1234)));
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildGetUrl("/statistikk/handlingerbutikk");

        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
        List<ButikkCount> sumOverButikk = mapper.readValue(getBinaryContent(response), new TypeReference<List<ButikkCount>>() {});
        assertThat(sumOverButikk).isNotEmpty();
        assertEquals("Spar Fjellheimen", sumOverButikk.get(0).getButikk().getButikknavn());
    }

    @Test
    void testSisteHandelIButikk() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.sisteHandelIButikk()).thenReturn(Arrays.asList(new ButikkDate(new Butikk("Spar Fjellheimen"), new Date()), new ButikkDate(new Butikk("Joker Nord"), new Date())));
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildGetUrl("/statistikk/sistehandel");

        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
        List<ButikkDate> sumOverButikk = mapper.readValue(getBinaryContent(response), new TypeReference<List<ButikkDate>>() {});
        assertThat(sumOverButikk).isNotEmpty();
        assertEquals("Spar Fjellheimen", sumOverButikk.get(0).getButikk().getButikknavn());
    }

    @Test
    void testTotaltHandlebelopPrAar() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.totaltHandlebelopPrAar()).thenReturn(Arrays.asList(new SumYear(2345, Year.of(2001)), new SumYear(3241, Year.of(2002)), new SumYear(3241, Year.of(2003)), new SumYear(3241, Year.of(2004)), new SumYear(3241, Year.of(2005)), new SumYear(3241, Year.of(2006)), new SumYear(3241, Year.of(2007)), new SumYear(3241, Year.of(2008)), new SumYear(3241, Year.of(2009)), new SumYear(3241, Year.of(2010)), new SumYear(3241, Year.of(2011)), new SumYear(3241, Year.of(2012)), new SumYear(3241, Year.of(2013)), new SumYear(3241, Year.of(2014)), new SumYear(3241, Year.of(2015)), new SumYear(3241, Year.of(2016)), new SumYear(3241, Year.of(2017)), new SumYear(3241, Year.of(2018)), new SumYear(3241, Year.of(2019))));
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildGetUrl("/statistikk/sumyear");

        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
        List<SumYear> sumyear = mapper.readValue(getBinaryContent(response), new TypeReference<List<SumYear>>() {});
        assertThat(sumyear).isNotEmpty();
        assertEquals(Year.of(2001), sumyear.get(0).getYear());
    }

    @Test
    void testTotaltHandlebelopPrAarOgMaaned() throws Exception {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.totaltHandlebelopPrAarOgMaaned()).thenReturn(Arrays.asList(new SumYearMonth(234, Year.of(2001), Month.JANUARY),new SumYearMonth(234, Year.of(2001), Month.FEBRUARY),new SumYearMonth(234, Year.of(2001), Month.MARCH),new SumYearMonth(234, Year.of(2001), Month.APRIL),new SumYearMonth(234, Year.of(2001), Month.MAY),new SumYearMonth(234, Year.of(2001), Month.JUNE),new SumYearMonth(234, Year.of(2001), Month.JULY),new SumYearMonth(234, Year.of(2001), Month.AUGUST), new SumYearMonth(324, Year.of(2002), Month.SEPTEMBER), new SumYearMonth(324, Year.of(2003), Month.OCTOBER), new SumYearMonth(324, Year.of(2004), Month.NOVEMBER), new SumYearMonth(324, Year.of(2005), Month.DECEMBER), new SumYearMonth(324, Year.of(2006), Month.JANUARY), new SumYearMonth(324, Year.of(2007), Month.JANUARY), new SumYearMonth(324, Year.of(2008), Month.JANUARY), new SumYearMonth(324, Year.of(2009), Month.JANUARY), new SumYearMonth(324, Year.of(2010), Month.JANUARY), new SumYearMonth(324, Year.of(2011), Month.JANUARY), new SumYearMonth(324, Year.of(2012), Month.JANUARY), new SumYearMonth(324, Year.of(2013), Month.JANUARY), new SumYearMonth(324, Year.of(2014), Month.JANUARY), new SumYearMonth(324, Year.of(2015), Month.JANUARY), new SumYearMonth(324, Year.of(2016), Month.JANUARY), new SumYearMonth(324, Year.of(2017), Month.JANUARY), new SumYearMonth(324, Year.of(2018), Month.JANUARY), new SumYearMonth(324, Year.of(2019), Month.JANUARY)));
        MockLogService logservice = new MockLogService();
        HandleregWebApi servlet = simulateDSComponentActivationAndWebWhiteboardConfiguration(handlereg, logservice);
        MockHttpServletRequest request = buildGetUrl("/statistikk/sumyearmonth");

        MockHttpServletResponse response = new MockHttpServletResponse();

        loginUser(request, response, "jd", "johnnyBoi");
        servlet.service(request, response);
        assertEquals(200, response.getStatus());
        List<SumYearMonth> sumyearmonth = mapper.readValue(getBinaryContent(response), new TypeReference<List<SumYearMonth>>() {});
        assertThat(sumyearmonth).isNotEmpty();
        assertEquals(Year.of(2001), sumyearmonth.get(0).getYear());
    }

    private byte[] getBinaryContent(MockHttpServletResponse response) throws IOException {
        MockServletOutputStream outputstream = (MockServletOutputStream) response.getOutputStream();
        return outputstream.getBinaryContent();
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
        when(config.getInitParameter(eq(ServerProperties.PROVIDER_PACKAGES))).thenReturn("no.priv.bang.handlereg.web.api.resources");
        ServletContext servletContext = mock(ServletContext.class);
        when(servletContext.getContextPath()).thenReturn("/handlereg");
        when(config.getServletContext()).thenReturn(servletContext);
        when(servletContext.getAttributeNames()).thenReturn(Collections.emptyEnumeration());
        return config;
    }
}
