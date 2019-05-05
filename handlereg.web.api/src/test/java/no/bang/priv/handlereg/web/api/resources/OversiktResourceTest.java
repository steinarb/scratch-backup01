package no.bang.priv.handlereg.web.api.resources;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.ws.rs.InternalServerErrorException;

import org.junit.jupiter.api.Test;
import no.bang.priv.handlereg.services.HandleregService;
import no.bang.priv.handlereg.services.Oversikt;
import no.bang.priv.handlereg.web.api.ShiroTestBase;
import no.priv.bang.osgi.service.mocks.logservice.MockLogService;

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

    @Test
    void testGetOversiktNotLoggedIn() {
        MockLogService logservice = new MockLogService();
        HandleregService handlereg = mock(HandleregService.class);
        Oversikt jdOversikt = new Oversikt(1, "jd", "johndoe@gmail.com", "John", "Doe", 1500);
        when(handlereg.finnOversikt("jd")).thenReturn(jdOversikt);
        OversiktResource resource = new OversiktResource();
        resource.logservice = logservice;
        resource.handlereg = handlereg;
        removeWebSubjectFromThread();

        assertThrows(InternalServerErrorException.class, () -> {
                Oversikt oversikt = resource.get();
                assertEquals("jd", oversikt.getBrukernavn());
            });
    }

}
