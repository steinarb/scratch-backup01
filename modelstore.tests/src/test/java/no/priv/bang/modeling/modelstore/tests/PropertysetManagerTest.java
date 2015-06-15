package no.priv.bang.modeling.modelstore.tests;

import static org.junit.Assert.*;
import static org.ops4j.pax.exam.CoreOptions.junitBundles;
import static org.ops4j.pax.exam.CoreOptions.mavenBundle;
import static org.ops4j.pax.exam.CoreOptions.options;
import static org.ops4j.pax.exam.CoreOptions.systemProperty;

import java.util.UUID;

import javax.inject.Inject;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetManager;

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
    	UUID propertysetId = UUID.randomUUID();
    	// Create a new propertyset
        Propertyset propertyset = propertysetManagerService.findPropertyset(propertysetId);
        propertyset.setStringProperty("stringPropertyOfPi", "3.14");
        assertEquals(Double.valueOf(3.14), propertyset.getDoubleProperty("stringPropertyOfPi"));
        // Verify that using the same ID will result in the same propertyset
        Propertyset newPropertyset = propertysetManagerService.findPropertyset(propertysetId);
        assertEquals(propertyset, newPropertyset);
    }

}
