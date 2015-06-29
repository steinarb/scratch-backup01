package no.priv.bang.modeling.modelstore.impl;

import static no.priv.bang.modeling.modelstore.impl.Values.getNilPropertyset;
import static no.priv.bang.modeling.modelstore.impl.Values.newList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonFactory;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;

public class PropertysetContextImpl {

    private Map<UUID, Propertyset> propertysets = new HashMap<UUID, Propertyset>();
    private Set<Propertyset> embeddedAspects;

    public PropertysetContextImpl() {
        loadEmbeddedAspects();
    }

    public Propertyset createPropertyset() {
        return new PropertysetImpl();
    }

    public ValueList createList() {
        return newList();
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
        List<Propertyset> allPropertysetsExcludingEmbedded = new ArrayList<Propertyset>(propertysets.size());
        for (Propertyset propertyset : propertysets.values()) {
            if (!embeddedAspects.contains(propertyset)) {
                allPropertysetsExcludingEmbedded.add(propertyset);
            }
        }

        return allPropertysetsExcludingEmbedded;
    }

    public Collection<Propertyset> listAllAspects() {
        Set<Propertyset> allAspects = new HashSet<Propertyset>(embeddedAspects);
        for (Entry<UUID, Propertyset> propertyset : propertysets.entrySet()) {
            if (propertyset.getValue().hasAspect()) {
                ValueList aspects = propertyset.getValue().getAspects();
                for (Value value : aspects) {
                    Propertyset aspect = value.asReference();
                    if (!getNilPropertyset().equals(aspect)) {
                        allAspects.add(aspect);
                        Propertyset baseAspect = aspect.getReferenceProperty("inherits");
                        if (!getNilPropertyset().equals(baseAspect)) {
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
            ValueList aspectList = propertysetEntry.getValue().getListProperty("aspects");
            for (Value aspectValue : aspectList) {
                Set<Propertyset> aspectInheritanceChain = followInheritanceChain(aspectValue.asReference());
                if (aspectInheritanceChain.contains(aspect)) {
                    objectsOfAspect.add(propertysetEntry.getValue());
                }
            }
        }

        return objectsOfAspect;
    }

    private void loadEmbeddedAspects() {
        try {
            InputStream aspectsFile = getClass().getResourceAsStream("/json/aspects.json");
            JsonFactory jsonFactory = new JsonFactory();;
            JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);
            persister.restore(aspectsFile, this);
            embeddedAspects = new HashSet<Propertyset>(propertysets.values());
        } catch (Exception e) { }
    }

    Set<Propertyset> followInheritanceChain(Propertyset aspect) {
        Propertyset baseAspect = aspect.getReferenceProperty("inherits");
        if (!getNilPropertyset().equals(baseAspect)) {
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
