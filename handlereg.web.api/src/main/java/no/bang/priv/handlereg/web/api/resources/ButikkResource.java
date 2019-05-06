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
package no.bang.priv.handlereg.web.api.resources;

import java.util.List;

import javax.ws.rs.InternalServerErrorException;

import org.osgi.service.log.LogService;

import no.bang.priv.handlereg.services.Butikk;
import no.bang.priv.handlereg.services.HandleregService;

public class ButikkResource {

    LogService logservice;
    HandleregService handlereg;
    public List<Butikk> getButikker() {
        try {
            return handlereg.finnButikker();
        } catch (Exception e) {
            String message = "Failed to find the list of stores";
            logservice.log(LogService.LOG_ERROR, message, e);
            throw new InternalServerErrorException("Failed find the list of stores, see the log for details");
        }
    }

}
