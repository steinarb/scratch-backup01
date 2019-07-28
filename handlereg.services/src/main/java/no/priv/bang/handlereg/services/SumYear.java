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

import java.time.Year;

public class SumYear {

    private double sum;
    private Year year;

    public SumYear(double sum, Year year) {
        this.sum = sum;
        this.year = year;
    }

    public SumYear() {
        // No-args constructor required by jackson
    }

    public double getSum() {
        return sum;
    }

    public Year getYear() {
        return year;
    }

}
