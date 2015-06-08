package no.priv.bang.modeling.modelstore.impl;

import javax.inject.Provider;

import no.priv.bang.modeling.modelstore.PropertysetManager;
import no.steria.osgi.jsr330activator.Jsr330Activator;

/**
 * A thin wrapper around {@link DefaultPropertysetManager} that will
 * be picked up by the {@link Jsr330Activator} and be presented
 * in OSGi as a {@link PropertysetManager} service.
 *
 * @author Steinar Bang
 *
 */
public class PropertysetManagerProvider extends PropertysetManagerBase implements Provider<PropertysetManager> {

    public PropertysetManager get() {
        return this;
    }

}
