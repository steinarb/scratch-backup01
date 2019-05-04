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

import java.util.Date;

public class NyHandling {

    private String username;
    private int accountid = -1;
    private int storeId = -1;
    private double belop;
    private Date handletidspunkt;

    public NyHandling() {
        // No-args constructor required by jackson
    }

    public NyHandling(String username, int userId, int storeId, double belop, Date transactionTime) {
        this.username = username;
        this.accountid = userId;
        this.storeId = storeId;
        this.belop = belop;
        this.handletidspunkt = transactionTime;
    }

    public String getUsername() {
        return username;
    }

    public int getAccountid() {
        return accountid;
    }

    public int getStoreId() {
        return storeId;
    }

    public double getBelop() {
        return belop;
    }

    public Date getHandletidspunkt() {
        return handletidspunkt;
    }

}
