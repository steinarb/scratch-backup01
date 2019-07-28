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

import java.util.Date;

public class ButikkDate {

    private Butikk butikk;
    private Date date;

    public ButikkDate(Butikk butikk, Date date) {
        this.butikk = butikk;
        this.date = date;
    }

    public ButikkDate() {
        // No-args constructor required by jackson
    }

    public Butikk getButikk() {
        return butikk;
    }

    public Date getDate() {
        return date;
    }

}
