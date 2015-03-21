package no.priv.bang.modeling.modelstore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import no.priv.bang.modeling.modelstore.impl.PropertysetImpl;

/**
 * Singleton instance implementing {@link PropertysetManager}.
 *
 * @author Steinar Bang
 *
 */
final class DefaultPropertysetManager implements PropertysetManager {

    private static DefaultPropertysetManager singleton;
    private Map<UUID, Propertyset> propertysets = new HashMap<UUID, Propertyset>();

    private DefaultPropertysetManager() { }

    public static PropertysetManager getInstance() {
        if (null == singleton) {
            singleton = new DefaultPropertysetManager();
        }

        return singleton;
    }

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

}
