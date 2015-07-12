package no.priv.bang.modeling.modelstore.impl;

import static no.priv.bang.modeling.modelstore.impl.Values.getNilPropertyset;
import static no.priv.bang.modeling.modelstore.impl.Values.newList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonFactory;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetContext;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;

public class PropertysetContextImpl implements PropertysetContext {

    protected final UUID metadataId = UUID.fromString("b1ad694b-4003-412b-8249-a7d1a0a24cf3");
    private Map<UUID, Propertyset> propertysets = new HashMap<UUID, Propertyset>();
    private Set<Propertyset> embeddedAspects;

    public PropertysetContextImpl() {
        loadEmbeddedAspects();
    }

    /* (non-Javadoc)
     * @see no.priv.bang.modeling.modelstore.impl.PropertysetContext#createPropertyset()
     */
    public Propertyset createPropertyset() {
        return new PropertysetImpl();
    }

    /* (non-Javadoc)
     * @see no.priv.bang.modeling.modelstore.impl.PropertysetContext#createList()
     */
    public ValueList createList() {
        return newList();
    }

    /* (non-Javadoc)
     * @see no.priv.bang.modeling.modelstore.impl.PropertysetContext#findPropertyset(java.util.UUID)
     */
    public Propertyset findPropertyset(UUID id) {
        Propertyset propertyset = propertysets.get(id);
        if (null == propertyset) {
            propertyset = new PropertysetImpl(id);
            propertysets.put(id, propertyset);
        }

        return propertyset;
    }

    /* (non-Javadoc)
     * @see no.priv.bang.modeling.modelstore.impl.PropertysetContext#listAllPropertysets()
     */
    public Collection<Propertyset> listAllPropertysets() {
        List<Propertyset> allPropertysetsExcludingEmbedded = new ArrayList<Propertyset>(propertysets.size());
        for (Propertyset propertyset : propertysets.values()) {
            if (!embeddedAspects.contains(propertyset)) {
                allPropertysetsExcludingEmbedded.add(propertyset);
            }
        }

        return allPropertysetsExcludingEmbedded;
    }

    /* (non-Javadoc)
     * @see no.priv.bang.modeling.modelstore.impl.PropertysetContext#listAllAspects()
     */
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

    /* (non-Javadoc)
     * @see no.priv.bang.modeling.modelstore.impl.PropertysetContext#findObjectsOfAspect(no.priv.bang.modeling.modelstore.Propertyset)
     */
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

    public Date getLastmodifieddate(Propertyset propertyset) {
        // This class does not store metadata for its propertysets
        return null;
    }

}
