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

import java.util.Date;

import org.junit.jupiter.api.Test;

class NyHandlingTest {

    @Test
    void testAllValues() {
        String username = "jad";
        int userId = 2;
        int storeId = 2;
        double beløp = 42.0;
        Date now = new Date();
        NyHandling bean = new NyHandling(username, userId, storeId, beløp, now);
        assertEquals(username, bean.getUsername());
        assertEquals(userId, bean.getUserId());
        assertEquals(storeId, bean.getStoreId());
        assertEquals(beløp, bean.getBelop(), 1.0);
        assertEquals(now, bean.getTransactionTime());
    }

    @Test
    void testNoArgsConstructor() {
        NyHandling bean = new NyHandling();
        assertNull(bean.getUsername());
        assertEquals(-1, bean.getUserId());
        assertEquals(-1, bean.getStoreId());
        assertEquals(0.0, bean.getBelop(), 1.0);
        assertNull(bean.getTransactionTime());
    }

}
