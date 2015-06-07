package no.priv.bang.modeling.modelstore;

import java.util.Collection;
import java.util.UUID;

/**
 * An interface that defines the access to all {@link Propertyset} instances
 * in memory.
 *
 * This interface can be used to create new propertysets, and it can be used
 * to retrieve existing propertysets.
 *
 * @author Steinar Bang
 *
 */
public interface PropertysetManager {

    /**
     * Create a new propertyset without id.
     *
     * @return a {@link PropertySet} without an id property set
     */
    Propertyset createPropertyset();

    /**
     * Return a propertyset identified by an {@link UUID}, or create a
     * new propertyset with the id set if none can be found.
     *
     * A typical use case for creating a new propertyset with an id,
     * is when parsing a file containing a UUID reference.  In that case
     * the propertyset is initially created and then later filled out
     * with parsed data.
     *
     * @param id identifying a {@link Propertyset}
     * @return a {@link Propertyset} with a property "id" containing the id parameter
     */
    Propertyset findPropertyset(UUID id);

    /**
     * Find all "aspects" that are present in memory.
     *
     * An aspect is a {@link Propertyset} holding property definitions (name
     * and type of the property) of a propertyset.  The aspect can be used
     * to add more semantics to a propertyset than is actually found on on
     * the propertyset instances.
     *
     * Aspects have inheritance.
     *
     * @return a collection of {@link Propertyset} instances holding propertyset descriptions
     */
    Collection<Propertyset> listAllAspects();

    /**
     * Find all propertysets that confirm to an aspect (or, to have had
     * an aspect applied to them.  I haven't decided on the terminology
     * here).
     *
     * The method will list both propertyset matching a particular
     * aspect, and the aspects that form its supertypes.
     *
     * @param aspect A {@link Propertyset} containing a propertyset definition
     * @return the propertysets in memory that conform to the aspect definition
     */
    Collection<Propertyset> findObjectsOfAspect(Propertyset aspect);

}
