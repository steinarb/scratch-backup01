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

import java.time.Year;

import org.junit.jupiter.api.Test;

class SumYearTest {

    @Test
    void test() {
        double sum = 2345;
        Year year = Year.of(2017);
        SumYear bean = new SumYear(sum, year);
        assertEquals(sum, bean.getSum());
        assertEquals(year, bean.getYear());
    }

    @Test
    void testNoargsConstructor() {
        SumYear bean = new SumYear();
        assertEquals(0, bean.getSum());
        assertNull(bean.getYear());
    }

}
