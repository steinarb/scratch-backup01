package no.priv.bang.modeling.modelstore;

import java.util.UUID;

public interface PropertysetManager {

    Propertyset createPropertyset();

    Propertyset findPropertyset(UUID id);

}
