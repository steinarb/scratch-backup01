package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

/**
 * Implementation of {@link Propertyset} backed by a {@link Map}.
 *
 * @author Steinar Bang
 *
 */
public class PropertysetImpl implements Propertyset {

	public boolean isNil() {
		return false;
	}

	public Integer getIntegerProperty(String propertyName) {
		return PropertysetNil.getNil().getIntegerProperty(propertyName);
	}

	public Double getDoubleProperty(String propertyName) {
		return PropertysetNil.getNil().getDoubleProperty(propertyName);
	}

	public String getStringProperty(String propertyName) {
		return PropertysetNil.getNil().getStringProperty(propertyName);
	}

	public Propertyset getComplexProperty(String string) {
		return PropertysetNil.getNil();
	}

	public Propertyset getReference(String string) {
		return PropertysetNil.getNil();
	}

}
