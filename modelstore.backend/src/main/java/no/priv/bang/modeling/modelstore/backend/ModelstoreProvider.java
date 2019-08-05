package no.priv.bang.modeling.modelstore.backend;

import javax.inject.Provider;

import no.priv.bang.modeling.modelstore.services.Modelstore;

/**
 * A thin wrapper around {@link ModelstoreBase} that will
 * be picked up by the {@link Jsr330Activator} and be presented
 * in OSGi as a {@link Modelstore} service.
 *
 * @author Steinar Bang
 *
 */
public class ModelstoreProvider extends ModelstoreBase implements Provider<Modelstore> {

    public Modelstore get() {
        return this;
    }

}
