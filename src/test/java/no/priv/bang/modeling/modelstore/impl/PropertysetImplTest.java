package no.priv.bang.modeling.modelstore.impl;

import static org.junit.Assert.*;
import no.priv.bang.modeling.modelstore.Propertyset;

import org.junit.Test;

public class PropertysetImplTest {

	/**
	 * Test creating an empty {@link PropertysetImpl} class and verify
	 * the expected empty values for all property types.
	 */
	@Test
	public void testCreateEmptyPropertyset() {
		Propertyset propertyset = new PropertysetImpl();
	    assertFalse(propertyset.isNil());

	    Integer intProperty = propertyset.getIntegerProperty("intPropName");
	    assertEquals(new Integer(0), intProperty);
	    Double doubleProperty = propertyset.getDoubleProperty("doublePropName");
	    assertEquals(new Double(0), doubleProperty);
	    String stringProperty = propertyset.getStringProperty("stringProperty");
	    assertEquals("", stringProperty);
	    Propertyset complexProperty = propertyset.getComplexProperty("complexProperty");
	    assertNotNull(complexProperty);
	    assertTrue(complexProperty.isNil());
	    Propertyset reference = propertyset.getReference("reference");
	    assertNotNull(reference);
	    assertTrue(reference.isNil());
	}

}
