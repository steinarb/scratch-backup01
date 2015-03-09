package no.priv.bang.modeling.modelstore;

/**
 * Interface defining a class that can function as both nodes
 * and edges in a model graph.
 *
 * The graph shold be easy to work and there should be no
 * need to check for null values: the typed property accessors
 * will never return null.
 *
 * The raw property accessors will return null values for unset
 * properties, but the raw accessors are mainly intended for reflection.s
 *
 * @author Steinar Bang
 *
 */
public interface Propertyset {

	boolean isNil();

	Integer getIntegerProperty(String propertyName);

	Double getDoubleProperty(String propertyName);

	String getStringProperty(String propertyName);

	Propertyset getComplexProperty(String propertyName);

	Propertyset getReference(String propertyName);

}
