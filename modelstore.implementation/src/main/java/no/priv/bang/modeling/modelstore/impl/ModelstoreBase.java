package no.priv.bang.modeling.modelstore.impl;

import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Provider;

import com.fasterxml.jackson.core.JsonFactory;

import no.priv.bang.modeling.modelstore.PropertysetContext;
import no.priv.bang.modeling.modelstore.Modelstore;

/**
 * Class implementing {@link Modelstore} for use as a base
 * class for {@link Provider} classes for {@link Modelstore}
 *
 * @author Steinar Bang
 *
 */
class ModelstoreBase implements Modelstore {

    private PropertysetContext context = new PropertysetContextImpl();

    protected ModelstoreBase() {
    }

    public PropertysetContext getDefaultContext() {
        return context;
    }

    public PropertysetContext restoreContext(InputStream jsonfilestream) {
        PropertysetContextImpl ctxt = new PropertysetContextImpl();
        JsonFactory jsonFactory = new JsonFactory();
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);
        persister.restore(jsonfilestream, ctxt);

        return new PropertysetContextRecordingMetadata(ctxt);
    }

    public void persistContext(OutputStream jsonfilestream, PropertysetContext context) {
        JsonFactory jsonFactory = new JsonFactory();
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);
        persister.persist(jsonfilestream, context);
    }

}
