package no.priv.bang.modeling.modelstore.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.inject.Provider;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetManager;
import no.priv.bang.modeling.modelstore.PropertysetNil;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.impl.PropertysetImpl;

/**
 * Class implementing {@link PropertysetManager} for use as a base
 * class for {@link Provider} classes for {@link PropertysetManager}
 *
 * @author Steinar Bang
 *
 */
class PropertysetManagerBase implements PropertysetManager {

    private Map<UUID, Propertyset> propertysets = new HashMap<UUID, Propertyset>();

    protected PropertysetManagerBase() { }

    public Propertyset createPropertyset() {
        return new PropertysetImpl();
    }

    public Propertyset findPropertyset(UUID id) {
        Propertyset propertyset = propertysets.get(id);
        if (null == propertyset) {
            propertyset = new PropertysetImpl(id);
            propertysets.put(id, propertyset);
        }

        return propertyset;
    }

    public Collection<Propertyset> listAllPropertysets() {
    	return propertysets.values();
    }

    public Collection<Propertyset> listAllAspects() {
        Set<Propertyset> allAspects = new HashSet<Propertyset>();
        for (Entry<UUID, Propertyset> propertyset : propertysets.entrySet()) {
            if (propertyset.getValue().hasAspect()) {
                PropertyvalueList aspects = propertyset.getValue().getAspects();
                for (Propertyvalue propertyvalue : aspects) {
                    Propertyset aspect = propertyvalue.asReference();
                    if (!PropertysetNil.getNil().equals(aspect)) {
                        allAspects.add(aspect);
                        Propertyset baseAspect = aspect.getReferenceProperty("inherits");
                        if (!PropertysetNil.getNil().equals(baseAspect)) {
                            allAspects.add(baseAspect);
                        }
                    }
                }
            }
        }

        return allAspects;
    }

    public Collection<Propertyset> findObjectsOfAspect(Propertyset aspect) {
        List<Propertyset> objectsOfAspect = new ArrayList<Propertyset>();
        for (Entry<UUID, Propertyset> propertysetEntry : propertysets.entrySet()) {
            PropertyvalueList aspectList = propertysetEntry.getValue().getListProperty("aspects");
            for (Propertyvalue aspectValue : aspectList) {
            	Set<Propertyset> aspectInheritanceChain = followInheritanceChain(aspectValue.asReference());
                if (aspectInheritanceChain.contains(aspect)) {
                    objectsOfAspect.add(propertysetEntry.getValue());
                }
            }
        }

        return objectsOfAspect;
    }

    private Set<Propertyset> followInheritanceChain(Propertyset aspect) {
        Propertyset baseAspect = aspect.getReferenceProperty("inherits");
        if (!PropertysetNil.getNil().equals(baseAspect)) {
            Set<Propertyset> aspects = followInheritanceChain(baseAspect);
            aspects.add(aspect);
            return aspects;
        } else {
            // No more base aspects, create the set and add myself
            Set<Propertyset> aspects = new HashSet<Propertyset>();
            aspects.add(aspect);
            return aspects;
        }
    }

}
