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

public class Butikk {

    private int storeId = -1;
    private String butikknavn;
    private int gruppe;
    private int rekkefolge;

    public Butikk() {
        // No-args constructor required by jackson
    }

    public Butikk(int storeId, String storeName, int gruppe, int rekkefolge) {
        this.storeId = storeId;
        this.butikknavn = storeName;
        this.gruppe = gruppe;
        this.rekkefolge = rekkefolge;
    }

    public Butikk(String butikknavn, int gruppe, int rekkefolge) {
        this.butikknavn = butikknavn;
        this.gruppe = gruppe;
        this.rekkefolge = rekkefolge;
    }

    public Butikk(String butikknavn) {
        this.butikknavn = butikknavn;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getButikknavn() {
        return butikknavn;
    }

    public int getGruppe() {
        return gruppe;
    }

    public int getRekkefolge() {
        return rekkefolge;
    }

}
