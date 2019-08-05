package no.priv.bang.modeling.modelstore.impl;

import java.util.UUID;

import static no.priv.bang.modeling.modelstore.impl.Propertysets.*;
import static org.junit.Assert.*;
import no.priv.bang.modeling.modelstore.ModelContext;
import no.priv.bang.modeling.modelstore.Modelstore;
import no.priv.bang.modeling.modelstore.Propertyset;

import org.junit.Test;

/**
 * Unit tests for class {@link Propertysets}.
 *
 * @author Steinar Bang
 *
 */
public class PropertysetsTest {
    /**
     * Unit tests for {@link Propertysets#findWrappedPropertyset(no.priv.bang.modeling.modelstore.Propertyset)}.
     */
    @Test
    public void testFindWrappedPropertyset() {
        Modelstore modelstore = new ModelstoreProvider().get();
        ModelContext context = modelstore.createContext();

        // Test unwrapping of a wrapped propertyset
        Propertyset wrappedPropertyset = context.findPropertyset(UUID.randomUUID());
        Propertyset unwrappedPropertyset = findWrappedPropertyset(wrappedPropertyset);
        assertNotSame(wrappedPropertyset, unwrappedPropertyset); // Not the same object
        assertEquals(wrappedPropertyset, unwrappedPropertyset); // A wrapper tests equal to the propertyset it wraps

        // Test that an unwrapped propertyset comes through as itself
        Propertyset unwrappedPropertyset2 = findWrappedPropertyset(unwrappedPropertyset);
        assertSame(unwrappedPropertyset, unwrappedPropertyset2);
    }
}
