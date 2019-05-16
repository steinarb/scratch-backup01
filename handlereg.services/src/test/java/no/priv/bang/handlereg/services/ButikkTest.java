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
package no.bang.priv.handlereg.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ButikkTest {

    @Test
    void testAllValues() {
        int storeId = 1;
        String storeName = "Spar fjelheimen";
        int gruppe = 2;
        int rekkefølge = 140;
        Butikk bean = new Butikk(storeId, storeName, gruppe, rekkefølge);
        assertEquals(storeId, bean.getStoreId());
        assertEquals(storeName, bean.getButikknavn());
        assertEquals(gruppe, bean.getGruppe());
        assertEquals(rekkefølge, bean.getRekkefolge());
    }

    @Test
    void testConstructorWithoutId() {
        String storeName = "Spar fjelheimen";
        int gruppe = 2;
        int rekkefølge = 140;
        Butikk bean = new Butikk(storeName, gruppe, rekkefølge);
        assertEquals(storeName, bean.getButikknavn());
        assertEquals(gruppe, bean.getGruppe());
        assertEquals(rekkefølge, bean.getRekkefolge());
    }

    @Test
    void testNameOnlyConstructor() {
        String storeName = "Spar fjelheimen";
        Butikk bean = new Butikk(storeName);
        assertEquals(storeName, bean.getButikknavn());
    }

    @Test
    void testNoArgsConstructor() {
        Butikk bean = new Butikk();
        assertEquals(-1, bean.getStoreId());
        assertNull(bean.getButikknavn());
        assertEquals(0, bean.getGruppe());
        assertEquals(0, bean.getRekkefolge());
    }

}
