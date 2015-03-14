package no.priv.bang.modeling.modelstore.impl;

import java.util.HashMap;
import java.util.Map;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetNil;

/**
 * Implementation of {@link Propertyset} backed by a {@link Map}.
 *
 * @author Steinar Bang
 *
 */
public class PropertysetImpl implements Propertyset {
	Map<String, Object> properties = new HashMap<String, Object>();

	public boolean isNil() {
		return false;
	}

	public Integer getIntegerProperty(String propertyName) {
		Integer propertyValue = PropertysetNil.getNil().getIntegerProperty(propertyName);
		Object rawPropertyValue = properties.get(propertyName);
		if (null != rawPropertyValue) {
			if (rawPropertyValue.getClass() == Integer.class) {
				propertyValue = (Integer) rawPropertyValue;
			} else {
				propertyValue = transformPropertyValueToInteger(rawPropertyValue);
			}
		}

		return propertyValue;
	}

	private Integer transformPropertyValueToInteger(Object rawPropertyValue) {
		Integer propertyValue = PropertysetNil.getNil().getIntegerProperty("dummy");
		Class<? extends Object> rawPropertyValueClass = rawPropertyValue.getClass();

		if (rawPropertyValueClass == Double.class) {
			Double doublePropertyValue = (Double) rawPropertyValue;
			propertyValue = Integer.valueOf((int) Math.round(doublePropertyValue.doubleValue()));
		}

		if (rawPropertyValueClass == String.class) {
			String stringPropertyValue = (String) rawPropertyValue;
			try {
				propertyValue = Integer.valueOf(stringPropertyValue);
			} catch (NumberFormatException e) {
				try {
					double doubleValue = Double.parseDouble(stringPropertyValue);
					propertyValue = Integer.valueOf((int) Math.round(doubleValue));
				} catch (NumberFormatException e1) { }
			}
		}

		return propertyValue;
	}

	public void setIntegerProperty(String propertyName, Integer intValue) {
		properties.put(propertyName, intValue);
	}

	public Double getDoubleProperty(String propertyName) {
		Double propertyValue = PropertysetNil.getNil().getDoubleProperty(propertyName);
		Object rawPropertyValue = properties.get(propertyName);
		if (null != rawPropertyValue) {
			if (rawPropertyValue.getClass() == Double.class) {
				propertyValue = (Double) rawPropertyValue;
			} else {
				propertyValue = transformPropertyValueToDouble(rawPropertyValue);
			}
		}

		return propertyValue;
	}

	private Double transformPropertyValueToDouble(Object rawPropertyValue) {
		Class<? extends Object> propertyType = rawPropertyValue.getClass();
		if (propertyType == Integer.class) {
			Integer propertyAsInt = (Integer) rawPropertyValue;
			return Double.valueOf(propertyAsInt.intValue());
		}
		if (propertyType == String.class) {
			String propertyAsString = (String) rawPropertyValue;
			try {
				return Double.valueOf(propertyAsString);
			} catch (NumberFormatException e) {}
		}

		return PropertysetNil.getNil().getDoubleProperty("dummy");
	}

	public void setDoubleProperty(String propertyName, Double doubleValue) {
		properties.put(propertyName, doubleValue);
	}

	public String getStringProperty(String propertyName) {
		String stringValue = PropertysetNil.getNil().getStringProperty(propertyName);
		Object rawPropertyValue = properties.get(propertyName);
		if (null != rawPropertyValue) {
			if (rawPropertyValue.getClass() == String.class) {
				stringValue = (String) rawPropertyValue;
			} else {
				stringValue = transformPropertyToString(rawPropertyValue);
			}
		}
		return stringValue;
	}

	private String transformPropertyToString(Object rawPropertyValue) {
		String stringValue = PropertysetNil.getNil().getStringProperty("dummy");
		Class<? extends Object> propertyType = rawPropertyValue.getClass();
		if (
				propertyType == Integer.class ||
				propertyType == Double .class)
		{
			stringValue = rawPropertyValue.toString();
		}

		return stringValue;
	}

	public void setStringProperty(String propertyName, String stringValue) {
		properties.put(propertyName, stringValue);
	}

	public Propertyset getComplexProperty(String string) {
		return PropertysetNil.getNil();
	}

	public Propertyset getReference(String string) {
		return PropertysetNil.getNil();
	}

}
