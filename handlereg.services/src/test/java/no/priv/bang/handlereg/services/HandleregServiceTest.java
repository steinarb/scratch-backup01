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
package no.priv.bang.handlereg.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;


class HandleregServiceTest {

    @Test
    void testOfAllOfTheMethods() {
        HandleregService service = mock(HandleregService.class);
        String brukernavn = "jad";
        Oversikt oversikt =  service.finnOversikt(brukernavn);
        assertNull(oversikt);
        int userId = 2;
        List<Transaction> transactions = service.findLastTransactions(userId);
        assertEquals(0, transactions.size());
        NyHandling handling = new NyHandling();
        Oversikt nyoversikt = service.registrerHandling(handling);
        assertNull(nyoversikt);
        List<Butikk> butikker = service.finnButikker();
        assertEquals(0, butikker.size());
        Butikk nybutikk = new Butikk();
        List<Butikk> oppdaterteButikker = service.leggTilButikk(nybutikk);
        assertEquals(0, oppdaterteButikker.size());
    }

}
