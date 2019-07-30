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
package no.priv.bang.handlereg.services;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ButikkSumTest {

    @Test
    void test() {
        Butikk butikk = new Butikk("Spar Fjellheimen");
        double sum = 2345;
        ButikkSum bean = new ButikkSum(butikk, sum);
        assertEquals(butikk, bean.getButikk());
        assertEquals(sum, bean.getSum());
    }

    @Test
    void testNoargsConstructor() {
        ButikkSum bean = new ButikkSum();
        assertNull(bean.getButikk());
        assertEquals(0, bean.getSum());
    }

}
