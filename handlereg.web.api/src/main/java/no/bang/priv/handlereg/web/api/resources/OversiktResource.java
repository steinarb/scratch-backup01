package no.bang.priv.handlereg.web.api.resources;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import no.bang.priv.handlereg.services.HandleregService;
import no.bang.priv.handlereg.services.Oversikt;

public class OversiktResource {

    HandleregService handlereg;

    public Oversikt get() {
        Subject subject = SecurityUtils.getSubject();
        String brukernavn = (String) subject.getPrincipal();
        return handlereg.finnOversikt(brukernavn);
    }

}
