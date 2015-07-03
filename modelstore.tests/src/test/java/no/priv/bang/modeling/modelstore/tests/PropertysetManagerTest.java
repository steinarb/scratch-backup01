package no.priv.bang.modeling.modelstore.tests;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Collection;
import java.util.UUID;

import javax.inject.Inject;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetContext;
import no.priv.bang.modeling.modelstore.PropertysetManager;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Configuration;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.junit.PaxExam;
import org.ops4j.pax.exam.spi.reactors.ExamReactorStrategy;
import org.ops4j.pax.exam.spi.reactors.PerClass;

@RunWith(PaxExam.class)
@ExamReactorStrategy(PerClass.class)
public class PropertysetManagerTest extends ModelstoreIntegrationtestBase {

    @Inject
    private PropertysetManager propertysetManagerService;

    @Configuration
    public Option[] config() {
        return options(
                       systemProperty("logback.configurationFile").value("file:src/test/resources/logback.xml"),
                       mavenBundle("org.slf4j", "slf4j-api", "1.7.2"),
                       mavenBundle("ch.qos.logback", "logback-core", "1.0.4"),
                       mavenBundle("ch.qos.logback", "logback-classic", "1.0.4"),
                       mavenBundle("com.fasterxml.jackson.core", "jackson-core", "2.5.3"),
                       mavenBundle("no.priv.bang.modeling", "modelstore.implementation", getMavenProjectVersion()),
                       junitBundles());
    }

    @Test
    public void propertysetManagerIntegrationTest() {
    	// Verify that the service could be injected
    	assertNotNull(propertysetManagerService);

    	// Actually use the service to create some propertysets
    	PropertysetContext context = propertysetManagerService.getDefaultContext();
    	UUID propertysetId = UUID.randomUUID();
    	// Create a new propertyset
        Propertyset propertyset = context.findPropertyset(propertysetId);
        propertyset.setStringProperty("stringPropertyOfPi", "3.14");
        assertEquals(Double.valueOf(3.14), propertyset.getDoubleProperty("stringPropertyOfPi"));
        // Verify that using the same ID will result in the same propertyset
        Propertyset newPropertyset = context.findPropertyset(propertysetId);
        assertEquals(propertyset, newPropertyset);
    }

    @Test
    public void testList() {
    	PropertysetContext context = propertysetManagerService.getDefaultContext();
        ValueList list = context.createList();
        assertEquals(0, list.size());
        list.add(true);
        assertTrue(list.get(0).asBoolean());
        list.add(3.14);
        assertEquals(3.14, list.get(1).asDouble(), 0.0);

        Propertyset referencedPropertyset = context.findPropertyset(UUID.randomUUID());
        list.add(referencedPropertyset);
        assertTrue(list.get(2).isReference());

        Propertyset containedPropertyset = context.createPropertyset();
        list.add(containedPropertyset);
        assertTrue(list.get(3).isComplexProperty());

        list.set(0, 42); // Overwrite boolean with long
        assertTrue(list.get(0).asBoolean()); // Non-null value gives true boolean
        assertEquals(42, list.get(0).asLong().longValue()); // The actual long value is still there

        ValueList containedlist = context.createList();
        containedlist.add(100);
        list.add(containedlist);
        assertEquals(1, list.get(4).asList().size());
        // Modifying original does not affect list element
        containedlist.add(2.5);
        assertEquals(2, containedlist.size());
        assertEquals(1, list.get(4).asList().size());

        assertEquals(5, list.size());
        list.remove(1);
        assertEquals(4, list.size());
    }

    @Test
    public void testEmbeddedAspects() {
    	PropertysetContext context = propertysetManagerService.getDefaultContext();
        int numberOfEmbeddedAspects = 5; // Adjust when adding embedded aspects

        Collection<Propertyset> aspects = context.listAllAspects();
        assertEquals(numberOfEmbeddedAspects, aspects.size());
    }

    /**
     * Test saving a {@link PropertysetContext} to a {@link PipedOutputStream}
     * and then restoring the context from a {@link PipedInputStream}.
     *
     * Have to write to the pipe from a different thread than the reader,
     * or else it will deadlock.
     *
     * @throws IOException
     */
    @Test
    public void testPersistRestorePropertysetContextUsingPipedStreams() throws IOException {
        InputStream carsAndBicylesStream = getClass().getResourceAsStream("/json/cars_and_bicycles.json");
        final PropertysetContext context1 = propertysetManagerService.restoreContext(carsAndBicylesStream);
        assertEquals(5, context1.listAllAspects().size());

        PipedInputStream loadStream = new PipedInputStream();
        final OutputStream saveStream = new PipedOutputStream(loadStream);
        new Thread(new Runnable() {
                public void run() {
                    propertysetManagerService.persistContext(saveStream, context1);
                }
            }).start();

        PropertysetContext context2 = propertysetManagerService.restoreContext(loadStream);

        compareAllPropertysets(context1, context2);
    }

    /**
     * Iterate over all of the {@link Propertyset} instances of a
     * {@link PropertysetManager} and compare them to the propertyesets
     * of a different PropertysetManager and assert that they match.
     *
     * @param context the {@link PropertysetContext} to iterate over
     * @param context2 the {@link PropertysetContext} to compare with
     */
    public static void compareAllPropertysets(PropertysetContext context, PropertysetContext context2) {
        for (Propertyset propertyset : context.listAllPropertysets()) {
            Propertyset parsedPropertyset = context2.findPropertyset(propertyset.getId());
            for (String propertyname : propertyset.getPropertynames()) {
                Value originalValue = propertyset.getProperty(propertyname);
                Value parsedValue = parsedPropertyset.getProperty(propertyname);
                assertEquals(originalValue, parsedValue);
            }
        }
    }

}
