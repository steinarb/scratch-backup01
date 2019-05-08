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

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.osgi.service.log.LogService;

import no.bang.priv.handlereg.services.Butikk;
import no.bang.priv.handlereg.services.HandleregService;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class ButikkResource {

    @Inject
    LogService logservice;

    @Inject
    HandleregService handlereg;

    @GET
    @Path("/butikker")
    public List<Butikk> getButikker() {
        try {
            return handlereg.finnButikker();
        } catch (Exception e) {
            String message = "Failed to find the list of stores";
            logservice.log(LogService.LOG_ERROR, message, e);
            throw new InternalServerErrorException(message + ", see the log for details");
        }
    }

    @POST
    @Path("/nybutikk")
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Butikk> leggTilButikk(Butikk nybutikk) {
        try {
            return handlereg.leggTilButikk(nybutikk);
        } catch (Exception e) {
            String message = "Failed to find add a new store";
            logservice.log(LogService.LOG_ERROR, message, e);
            throw new InternalServerErrorException(message + ", see the log for details");
        }
    }

}
