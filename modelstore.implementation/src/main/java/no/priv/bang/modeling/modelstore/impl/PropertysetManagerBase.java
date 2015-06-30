package no.priv.bang.modeling.modelstore.impl;

import java.util.Collection;
import java.util.UUID;

import javax.inject.Provider;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetContext;
import no.priv.bang.modeling.modelstore.PropertysetManager;
import no.priv.bang.modeling.modelstore.ValueList;

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

    public Propertyset createPropertyset() {
        return context.createPropertyset();
    }

    public ValueList createList() {
        return context.createList();
    }

    public Propertyset findPropertyset(UUID id) {
        return context.findPropertyset(id);
    }

    public Collection<Propertyset> listAllPropertysets() {
        return context.listAllPropertysets();
    }

    public Collection<Propertyset> listAllAspects() {
        return context.listAllAspects();
    }

    public Collection<Propertyset> findObjectsOfAspect(Propertyset aspect) {
        return context.findObjectsOfAspect(aspect);
    }

    public PropertysetContext getDefaultContext() {
        return context;
    }

}
