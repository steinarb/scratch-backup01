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
package no.bang.priv.handlereg.web.api.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import org.junit.jupiter.api.Test;

import no.bang.priv.handlereg.services.HandleregException;
import no.bang.priv.handlereg.services.HandleregService;
import no.bang.priv.handlereg.services.NyHandling;
import no.bang.priv.handlereg.services.Oversikt;
import no.bang.priv.handlereg.services.Transaction;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class HandlingResourceTest {

    @Test
    void testGetHandlinger() {
        MockLogService logservice = new MockLogService();
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.findLastTransactions(1)).thenReturn(Arrays.asList(new Transaction()));
        HandlingResource resource = new HandlingResource();
        resource.logservice = logservice;
        resource.handlereg = handlereg;
        List<Transaction> handlinger = resource.getHandlinger(1);
        assertThat(handlinger.size()).isGreaterThan(0);
    }

    @Test
    void testGetHandlingerEmpty() {
        MockLogService logservice = new MockLogService();
        HandleregService handlereg = mock(HandleregService.class);
        HandlingResource resource = new HandlingResource();
        resource.logservice = logservice;
        resource.handlereg = handlereg;
        List<Transaction> handlinger = resource.getHandlinger(1);
        assertEquals(0, handlinger.size());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testGetHandlingerWhenExceptionIsThrown() {
        MockLogService logservice = new MockLogService();
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.findLastTransactions(anyInt())).thenThrow(HandleregException.class);
        HandlingResource resource = new HandlingResource();
        resource.logservice = logservice;
        resource.handlereg = handlereg;

        assertThrows(InternalServerErrorException.class, () -> {
                List<Transaction> handlinger = resource.getHandlinger(1);
                assertEquals(0, handlinger.size());
            });
    }

    @Test
    void testNyhandling() {
        MockLogService logservice = new MockLogService();
        HandleregService handlereg = mock(HandleregService.class);
        Oversikt oversikt = new Oversikt(1, "jd", "johndoe@gmail.com", "John", "Doe", 500);
        when(handlereg.registrerHandling(any())).thenReturn(oversikt);
        HandlingResource resource = new HandlingResource();
        resource.logservice = logservice;
        resource.handlereg = handlereg;
        NyHandling handling = new NyHandling("jd", 1, 1, 510, new Date());
        Oversikt oppdatertOversikt = resource.nyhandling(handling);
        assertEquals(oversikt.getBalanse(), oppdatertOversikt.getBalanse());
    }

    @SuppressWarnings("unchecked")
    @Test
    void testNyhandlingWhenExceptionIsThrown() {
        MockLogService logservice = new MockLogService();
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.registrerHandling(any())).thenThrow(HandleregException.class);
        HandlingResource resource = new HandlingResource();
        resource.logservice = logservice;
        resource.handlereg = handlereg;
        NyHandling handling = new NyHandling("jd", 1, 1, 510, new Date());
        assertThrows(InternalServerErrorException.class, () -> {
                Oversikt oppdatertOversikt = resource.nyhandling(handling);
                assertEquals(0, oppdatertOversikt.getBalanse());
            });
    }

}
