package no.priv.bang.modeling.modelstore.impl;

import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Provider;

import com.fasterxml.jackson.core.JsonFactory;

import no.priv.bang.modeling.modelstore.ModelContext;
import no.priv.bang.modeling.modelstore.Modelstore;

/**
 * Class implementing {@link Modelstore} for use as a base
 * class for {@link Provider} classes for {@link Modelstore}
 *
 * @author Steinar Bang
 *
 */
class ModelstoreBase implements Modelstore {

    private ModelContext context = new ModelContextImpl();

    protected ModelstoreBase() {
    }

    public ModelContext getDefaultContext() {
        return context;
    }

    public ModelContext restoreContext(InputStream jsonfilestream) {
        ModelContextImpl ctxt = new ModelContextImpl();
        JsonFactory jsonFactory = new JsonFactory();
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);
        persister.restore(jsonfilestream, ctxt);

        return new ModelContextRecordingMetadata(ctxt);
    }

    public void persistContext(OutputStream jsonfilestream, ModelContext context) {
        JsonFactory jsonFactory = new JsonFactory();
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);
        persister.persist(jsonfilestream, context);
    }

}
