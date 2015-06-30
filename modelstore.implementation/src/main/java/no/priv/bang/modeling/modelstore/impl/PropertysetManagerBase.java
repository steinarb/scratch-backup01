package no.priv.bang.modeling.modelstore.impl;

import javax.inject.Provider;

import no.priv.bang.modeling.modelstore.PropertysetContext;
import no.priv.bang.modeling.modelstore.PropertysetManager;

/**
 * Class implementing {@link PropertysetManager} for use as a base
 * class for {@link Provider} classes for {@link PropertysetManager}
 *
 * @author Steinar Bang
 *
 */
class PropertysetManagerBase implements PropertysetManager {

    private PropertysetContext context = new PropertysetContextImpl();

    protected PropertysetManagerBase() {
    }

    public PropertysetContext getDefaultContext() {
        return context;
    }

}
