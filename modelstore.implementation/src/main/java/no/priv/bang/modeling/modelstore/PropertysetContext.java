package no.priv.bang.modeling.modelstore;

import java.util.Collection;
import java.util.UUID;

import no.priv.bang.modeling.modelstore.impl.JsonPropertysetPersister;

/**
 * A PropertysetContext is a reference to a set of interconnected
 * {@link Propertyset} instances forming a model or several models.
 *
 * A PropertysetContext can be persisted with
 * {@link JsonPropertysetPersister#persist(java.io.File, PropertysetContext)} and
 * restored with
 * {@link JsonPropertysetPersister#restore(java.io.File, PropertysetContext)}.
 *
 * A {@link PropertysetContext} will keep track of the last modification
 * time of each {@link Propertyset} and when merging two PropertysetContexts
 * all {@link Propertyset}s will be merged, and when merging each
 * {@link Propertyset}, the {@link Propertyset} with the newest modification date
 * will win in case of conflict.
 *
 * @author Steinar Bang
 *
 */
public interface PropertysetContext {
    /**
     * Create a new empty {@link ValueList}.
     *
     * @return a {@link ValueList} instance
     */
    ValueList createList();

    /**
     * Create a new {@link Propertyset} instance that has no id
     * and can be used as a complex property value of another
     * {@link Propertyset}.
     *
     * @return a {@link Propertyset} instance
     */
    Propertyset createPropertyset();

    /**
     * Return a {@link Propertyset} with a {@link Propertyset#getId()}
     * matching the argument. If no matching {@link Propertyset} can
     * be found a new one will be created, empty except for the id.
     *
     * @param id a {@link UUID} identifying a {@link Propertyset}
     * @return a {@link Propertyset} matching the id argument
     */
    Propertyset findPropertyset(UUID id);

    /**
     * List all of the {@link Propertyset} instances with ids,
     * except for the aspects that is built into every
     * {@link Propertyset}.
     *
     * @return a collection of {@link Propertyset} instances
     */
    Collection<Propertyset> listAllPropertysets();

    /**
     * List all of the {@link Propertyset} instances with ids
     * that contain aspect descriptions in this context.
     *
     * Aspect definitons follow a structure similar to
     * JSON schemas.
     *
     * @return a collection of {@link Propertyset} instances containing aspect definitions.
     */
    Collection<Propertyset> listAllAspects();

    /**
     * Find all of the {@link Propertyset} instances that have had
     * a particular aspect applied to it.  If the aspect is a base
     * object for other aspects, propertysets matching the child
     * aspects will also be returned.
     *
     * @param aspect the aspect to find propertysets matching
     * @return a collection of {@link Propertyset} instances
     */
    Collection<Propertyset> findObjectsOfAspect(Propertyset aspect);
}
