package no.priv.bang.modeling.modelstore;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface that defines the access to all {@link Propertyset} instances
 * in memory.
 *
 * This interface can be used to create new propertysets, and it can be used
 * to retrieve existing propertysets.
 *
 * @author Steinar Bang
 *
 */
public interface PropertysetManager {

    PropertysetContext getDefaultContext();
    PropertysetContext restoreContext(InputStream jsonfilestream);
    void persistContext(OutputStream jsonfilestream, PropertysetContext context);

}
