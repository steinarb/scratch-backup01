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
package no.priv.bang.handlereg.web.api.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.time.Month;
import java.time.Year;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import no.priv.bang.handlereg.services.Butikk;
import no.priv.bang.handlereg.services.ButikkCount;
import no.priv.bang.handlereg.services.ButikkDate;
import no.priv.bang.handlereg.services.ButikkSum;
import no.priv.bang.handlereg.services.HandleregService;
import no.priv.bang.handlereg.services.SumYear;
import no.priv.bang.handlereg.services.SumYearMonth;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

class StatistikkResourceTest {

    @Test
    void testGetSumOverButikk() {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.sumOverButikk()).thenReturn(Arrays.asList(new ButikkSum(new Butikk("Spar Fjellheimen"), 3345), new ButikkSum(new Butikk("Joker Nord"), 1234)));
        MockLogService logservice = new MockLogService();
        StatistikkResource resource = new StatistikkResource();
        resource.handlereg = handlereg;
        resource.logservice = logservice;

        List<ButikkSum> sumOverButikk = resource.sumOverButikk();
        assertThat(sumOverButikk).isNotEmpty();
        assertEquals("Spar Fjellheimen", sumOverButikk.get(0).getButikk().getButikknavn());
    }

    @Test
    void testAntallHandlingerIButikk() {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.antallHandlingerIButikk()).thenReturn(Arrays.asList(new ButikkCount(new Butikk("Spar Fjellheimen"), 3345), new ButikkCount(new Butikk("Joker Nord"), 1234)));
        MockLogService logservice = new MockLogService();
        StatistikkResource resource = new StatistikkResource();
        resource.handlereg = handlereg;
        resource.logservice = logservice;

        List<ButikkCount> antallHandlingerIButikk = resource.antallHandlingerIButikk();
        assertThat(antallHandlingerIButikk).isNotEmpty();
        assertEquals("Spar Fjellheimen", antallHandlingerIButikk.get(0).getButikk().getButikknavn());
    }

    @Test
    void testSisteHandelIButikk() {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.sisteHandelIButikk()).thenReturn(Arrays.asList(new ButikkDate(new Butikk("Spar Fjellheimen"), new Date()), new ButikkDate(new Butikk("Joker Nord"), new Date())));
        MockLogService logservice = new MockLogService();
        StatistikkResource resource = new StatistikkResource();
        resource.handlereg = handlereg;
        resource.logservice = logservice;

        List<ButikkDate> sisteHandelIButikk = resource.sisteHandelIButikk();
        assertThat(sisteHandelIButikk).isNotEmpty();
        assertEquals("Spar Fjellheimen", sisteHandelIButikk.get(0).getButikk().getButikknavn());
    }

    @Test
    void testTotaltHandlebelopPrAar() {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.totaltHandlebelopPrAar()).thenReturn(Arrays.asList(new SumYear(2345, Year.of(2001)), new SumYear(3241, Year.of(2002)), new SumYear(3241, Year.of(2003)), new SumYear(3241, Year.of(2004)), new SumYear(3241, Year.of(2005)), new SumYear(3241, Year.of(2006)), new SumYear(3241, Year.of(2007)), new SumYear(3241, Year.of(2008)), new SumYear(3241, Year.of(2009)), new SumYear(3241, Year.of(2010)), new SumYear(3241, Year.of(2011)), new SumYear(3241, Year.of(2012)), new SumYear(3241, Year.of(2013)), new SumYear(3241, Year.of(2014)), new SumYear(3241, Year.of(2015)), new SumYear(3241, Year.of(2016)), new SumYear(3241, Year.of(2017)), new SumYear(3241, Year.of(2018)), new SumYear(3241, Year.of(2019))));
        MockLogService logservice = new MockLogService();
        StatistikkResource resource = new StatistikkResource();
        resource.handlereg = handlereg;
        resource.logservice = logservice;

        List<SumYear> totaltHandlebelopPrAar = resource.totaltHandlebelopPrAar();
        assertThat(totaltHandlebelopPrAar).isNotEmpty();
        assertEquals(Year.of(2001), totaltHandlebelopPrAar.get(0).getYear());
    }

    @Test
    void testTotaltHandlebelopPrAarOgMaaned() {
        HandleregService handlereg = mock(HandleregService.class);
        when(handlereg.totaltHandlebelopPrAarOgMaaned()).thenReturn(Arrays.asList(new SumYearMonth(234, Year.of(2001), Month.JANUARY),new SumYearMonth(234, Year.of(2001), Month.FEBRUARY),new SumYearMonth(234, Year.of(2001), Month.MARCH),new SumYearMonth(234, Year.of(2001), Month.APRIL),new SumYearMonth(234, Year.of(2001), Month.MAY),new SumYearMonth(234, Year.of(2001), Month.JUNE),new SumYearMonth(234, Year.of(2001), Month.JULY),new SumYearMonth(234, Year.of(2001), Month.AUGUST), new SumYearMonth(324, Year.of(2002), Month.SEPTEMBER), new SumYearMonth(324, Year.of(2003), Month.OCTOBER), new SumYearMonth(324, Year.of(2004), Month.NOVEMBER), new SumYearMonth(324, Year.of(2005), Month.DECEMBER), new SumYearMonth(324, Year.of(2006), Month.JANUARY), new SumYearMonth(324, Year.of(2007), Month.JANUARY), new SumYearMonth(324, Year.of(2008), Month.JANUARY), new SumYearMonth(324, Year.of(2009), Month.JANUARY), new SumYearMonth(324, Year.of(2010), Month.JANUARY), new SumYearMonth(324, Year.of(2011), Month.JANUARY), new SumYearMonth(324, Year.of(2012), Month.JANUARY), new SumYearMonth(324, Year.of(2013), Month.JANUARY), new SumYearMonth(324, Year.of(2014), Month.JANUARY), new SumYearMonth(324, Year.of(2015), Month.JANUARY), new SumYearMonth(324, Year.of(2016), Month.JANUARY), new SumYearMonth(324, Year.of(2017), Month.JANUARY), new SumYearMonth(324, Year.of(2018), Month.JANUARY), new SumYearMonth(324, Year.of(2019), Month.JANUARY)));
        MockLogService logservice = new MockLogService();
        StatistikkResource resource = new StatistikkResource();
        resource.handlereg = handlereg;
        resource.logservice = logservice;

        List<SumYearMonth> totaltHandlebelopPrAarOgMaaned = resource.totaltHandlebelopPrAarOgMaaned();
        assertThat(totaltHandlebelopPrAarOgMaaned).isNotEmpty();
        assertEquals(Year.of(2001), totaltHandlebelopPrAarOgMaaned.get(0).getYear());
    }

}
