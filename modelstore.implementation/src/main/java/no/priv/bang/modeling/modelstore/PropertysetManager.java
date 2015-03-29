package no.priv.bang.modeling.modelstore;

import java.util.Collection;
import java.util.UUID;

public interface PropertysetManager {

    Propertyset createPropertyset();

    Propertyset findPropertyset(UUID id);

    Collection<Propertyset> listAllAspects();

    Collection<Propertyset> findObjectsOfAspect(Propertyset car);

}
