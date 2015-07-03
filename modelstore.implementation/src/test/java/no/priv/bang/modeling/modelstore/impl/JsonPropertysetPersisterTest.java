package no.priv.bang.modeling.modelstore.impl;

import static no.priv.bang.modeling.modelstore.testutils.TestUtils.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.UUID;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetContext;
import no.priv.bang.modeling.modelstore.PropertysetManager;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;

/**
 * Unit tests for {@link JsonPropertysetPersister}.
 *
 * @author Steinar Bang
 *
 */
public class JsonPropertysetPersisterTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Parse a file with propertysets and aspects.
     * @throws URISyntaxException
     * @throws IOException
     * @throws JsonParseException
     */
    @Test
    public void testParseComplexFile() throws URISyntaxException, JsonParseException, IOException {
        PropertysetManager propertysetManager = new PropertysetManagerProvider();
        PropertysetContext context = propertysetManager.getDefaultContext();
        File carsAndBicycles = getResourceAsFile("/json/cars_and_bicycles.json");

        JsonFactory jsonFactory = new JsonFactory();
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(carsAndBicycles, context);

        assertEquals(8, context.listAllAspects().size());
        assertEquals(8, context.listAllPropertysets().size());
    }

    /**
     * Test parsing with the id field not first.
     * @throws URISyntaxException
     * @throws IOException
     * @throws JsonParseException
     */
    @Test
    public void testParseIdNotFirst() throws URISyntaxException, JsonParseException, IOException {
        PropertysetManager propertysetManager = new PropertysetManagerProvider();
        PropertysetContext context = propertysetManager.getDefaultContext();
        File carsAndBicyclesIdNotFirst = getResourceAsFile("/json/cars_and_bicycles_id_not_first.json");

        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(carsAndBicyclesIdNotFirst, context);

        assertEquals(8, context.listAllAspects().size());
        assertEquals(8, context.listAllPropertysets().size());

        // Verify that the results are identical to the ones with the id first
        PropertysetManager propertysetManager2 = new PropertysetManagerProvider();
        PropertysetContext context2 = propertysetManager2.getDefaultContext();
        File carsAndBicycles = getResourceAsFile("/json/cars_and_bicycles.json");
        persister.restore(carsAndBicycles, context2);
        compareAllPropertysets(context, context2);
    }

    /**
     * Test that boolean fields can be parsed.
     * @throws URISyntaxException
     * @throws IOException
     * @throws JsonParseException
     */
    @Test
    public void parseBooleanProperties() throws URISyntaxException, JsonParseException, IOException {
        PropertysetManager propertysetManager = new PropertysetManagerProvider();
        PropertysetContext context = propertysetManager.getDefaultContext();
        File withBoolean = getResourceAsFile("/json/with_boolean.json");
        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(withBoolean, context);

        // Check the parsed values
        Propertyset propertyset = context.listAllPropertysets().iterator().next();
        assertFalse(propertyset.getBooleanProperty("untrue"));
        assertTrue(propertyset.getBooleanProperty("unfalse"));

        // Output the boolean values to a different file
        File propertysetsFile = folder.newFile("boolean.json");
        persister.persist(propertysetsFile, context);

        // Read the file back in and compare it with the original
        PropertysetManager propertysetManager2 = new PropertysetManagerProvider();
        PropertysetContext context2 = propertysetManager2.getDefaultContext();
        persister.restore(propertysetsFile, context2);

        // Verify that the results of the second parse are identical to the first
        compareAllPropertysets(context, context2);
    }

    /**
     * Test that it works to parse JSON where the file doesn't start with an
     * array but with an object.
     *
     * This format is close to what the actual persist format will look like.
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws JsonParseException
     */
    @Test
    public void parseObjectOnTop() throws URISyntaxException, JsonParseException, IOException {
        PropertysetManager propertysetManager = new PropertysetManagerProvider();
        PropertysetContext context = propertysetManager.getDefaultContext();
        File withBoolean = getResourceAsFile("/json/object_on_top.json");
        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(withBoolean, context);

        // Check the parsed values
        assertEquals(2, context.listAllPropertysets().size());

        // Output the two propertysets to a different file
        File twoObjectsFile = folder.newFile("two_objects.json");
        persister.persist(twoObjectsFile, context);

        // Read the file back in and compare it with the original
        PropertysetManager propertysetManager2 = new PropertysetManagerProvider();
        PropertysetContext context2 = propertysetManager2.getDefaultContext();
        persister.restore(twoObjectsFile, context2);

        // Verify that the results of the second parse are identical to the first
        compareAllPropertysets(context, context2);
    }

    /**
     * Test parsing propertyset with a list property.
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws JsonParseException
     */
    @Test
    public void parseListProperty() throws URISyntaxException, JsonParseException, IOException {
        PropertysetManager propertysetManager = new PropertysetManagerProvider();
        PropertysetContext context = propertysetManager.getDefaultContext();
        File withListProperty = getResourceAsFile("/json/with_list_property.json");
        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(withListProperty, context);

        // Check the parsed values
        assertEquals(1, context.listAllPropertysets().size());
        Propertyset propertyset = context.listAllPropertysets().iterator().next();
        assertEquals(7, propertyset.getListProperty("listofthings").size());

        // Output the two propertysets to a different file
        File saveRestoreFile = folder.newFile("list_property.json");
        persister.persist(saveRestoreFile, context);

        // Read the file back in and compare it with the original
        PropertysetManager propertysetManager2 = new PropertysetManagerProvider();
        PropertysetContext context2 = propertysetManager2.getDefaultContext();
        persister.restore(saveRestoreFile, context2);

        // Verify that the results of the second parse are identical to the first
        assertEquals(1, context2.listAllPropertysets().size());
        Propertyset propertyset2 = context2.listAllPropertysets().iterator().next();
        assertEquals(7, propertyset2.getListProperty("listofthings").size());
        compareAllPropertysets(context, context2);
    }

    /**
     * Not actually a unit test.  Just a convenient way to create a new
     * {@link Propertyset} with an unique id and write it to the console.
     *
     * @throws IOException
     */
    @Ignore
    @Test
    public void generatePropertysetWithId() throws IOException {
        PropertysetManager propertysetManager = new PropertysetManagerProvider();
        PropertysetContext context = propertysetManager.getDefaultContext();
        context.findPropertyset(UUID.randomUUID());
        context.findPropertyset(UUID.randomUUID());
        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);
        File propertysetsFile = folder.newFile("propertyset.json");
        persister.persist(propertysetsFile, context);
        String contents = new String(Files.readAllBytes(propertysetsFile.toPath()));
        System.out.println(contents);
    }
}
