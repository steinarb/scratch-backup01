package no.bang.priv.handlereg.web.api.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import no.bang.priv.handlereg.services.HandleregService;
import no.bang.priv.handlereg.services.Oversikt;
import no.priv.bang.authservice.web.security.ShiroTestBase;

class OversiktResourceTest extends ShiroTestBase {

    @Test
    void testGetOversikt() {
        HandleregService handlereg = mock(HandleregService.class);
        Oversikt jdOversikt = new Oversikt(1, "jd", "johndoe@gmail.com", "John", "Doe", 1500);
        when(handlereg.finnOversikt("jd")).thenReturn(jdOversikt);
        OversiktResource resource = new OversiktResource();
        resource.handlereg = handlereg;
        loginUser("jd", "johnnyBoi");

        Oversikt oversikt = resource.get();
        assertEquals("jd", oversikt.getBrukernavn());
    }

}
