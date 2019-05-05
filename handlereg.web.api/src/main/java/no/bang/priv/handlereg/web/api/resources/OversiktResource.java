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
            logservice.log(LogService.LOG_ERROR, "Failed to get Oversikt in handlereg");
            throw new InternalServerErrorException("Failed to get Oversikt, see the log for details");
        }
    }

}
