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

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.osgi.service.log.LogService;

import no.bang.priv.handlereg.services.HandleregService;
import no.bang.priv.handlereg.services.Oversikt;

@Path("/oversikt")
@Produces(MediaType.APPLICATION_JSON)
public class OversiktResource {

    @Inject
    public LogService logservice;

    @Inject
    HandleregService handlereg;

    @GET
    public Oversikt get() {
        try {
            Subject subject = SecurityUtils.getSubject();
            String brukernavn = (String) subject.getPrincipal();
            return handlereg.finnOversikt(brukernavn);
        } catch (Exception e) {
            String message = "Failed to get Oversikt in handlereg";
            logservice.log(LogService.LOG_ERROR, message);
            throw new InternalServerErrorException(message + ", see the log for details");
        }
    }

}
