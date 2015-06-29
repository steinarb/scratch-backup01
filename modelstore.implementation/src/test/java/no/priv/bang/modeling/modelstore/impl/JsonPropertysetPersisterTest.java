package no.priv.bang.modeling.modelstore.impl;

import static no.priv.bang.modeling.modelstore.testutils.TestUtils.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.UUID;

import no.priv.bang.modeling.modelstore.Propertyset;
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
        File carsAndBicycles = getResourceAsFile("/json/cars_and_bicycles.json");

        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(carsAndBicycles, propertysetManager);

        assertEquals(8, propertysetManager.listAllAspects().size());
        assertEquals(8, propertysetManager.listAllPropertysets().size());
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
        File carsAndBicyclesIdNotFirst = getResourceAsFile("/json/cars_and_bicycles_id_not_first.json");

        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(carsAndBicyclesIdNotFirst, propertysetManager);

        assertEquals(8, propertysetManager.listAllAspects().size());
        assertEquals(8, propertysetManager.listAllPropertysets().size());

        // Verify that the results are identical to the ones with the id first
        PropertysetManager propertysetManager2 = new PropertysetManagerProvider();
        File carsAndBicycles = getResourceAsFile("/json/cars_and_bicycles.json");
        persister.restore(carsAndBicycles, propertysetManager2);
        compareAllPropertysets(propertysetManager, propertysetManager2);
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
        File withBoolean = getResourceAsFile("/json/with_boolean.json");
        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(withBoolean, propertysetManager);

        // Check the parsed values
        Propertyset propertyset = propertysetManager.listAllPropertysets().iterator().next();
        assertFalse(propertyset.getBooleanProperty("untrue"));
        assertTrue(propertyset.getBooleanProperty("unfalse"));

        // Output the boolean values to a different file
        File propertysetsFile = folder.newFile("boolean.json");
        persister.persist(propertysetsFile, propertysetManager);

        // Read the file back in and compare it with the original
        PropertysetManager propertysetManager2 = new PropertysetManagerProvider();
        persister.restore(propertysetsFile, propertysetManager2);

        // Verify that the results of the second parse are identical to the first
        compareAllPropertysets(propertysetManager, propertysetManager2);
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
        File withBoolean = getResourceAsFile("/json/object_on_top.json");
        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(withBoolean, propertysetManager);

        // Check the parsed values
        assertEquals(2, propertysetManager.listAllPropertysets().size());

        // Output the two propertysets to a different file
        File twoObjectsFile = folder.newFile("two_objects.json");
        persister.persist(twoObjectsFile, propertysetManager);

        // Read the file back in and compare it with the original
        PropertysetManager propertysetManager2 = new PropertysetManagerProvider();
        persister.restore(twoObjectsFile, propertysetManager2);

        // Verify that the results of the second parse are identical to the first
        compareAllPropertysets(propertysetManager, propertysetManager2);
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
        File withListProperty = getResourceAsFile("/json/with_list_property.json");
        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);

        // Read the contents of the file into memory
        persister.restore(withListProperty, propertysetManager);

        // Check the parsed values
        assertEquals(1, propertysetManager.listAllPropertysets().size());
        Propertyset propertyset = propertysetManager.listAllPropertysets().iterator().next();
        assertEquals(7, propertyset.getListProperty("listofthings").size());

        // Output the two propertysets to a different file
        File saveRestoreFile = folder.newFile("list_property.json");
        persister.persist(saveRestoreFile, propertysetManager);

        // Read the file back in and compare it with the original
        PropertysetManager propertysetManager2 = new PropertysetManagerProvider();
        persister.restore(saveRestoreFile, propertysetManager2);

        // Verify that the results of the second parse are identical to the first
        assertEquals(1, propertysetManager2.listAllPropertysets().size());
        Propertyset propertyset2 = propertysetManager2.listAllPropertysets().iterator().next();
        assertEquals(7, propertyset2.getListProperty("listofthings").size());
        compareAllPropertysets(propertysetManager, propertysetManager2);
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
        propertysetManager.findPropertyset(UUID.randomUUID());
        propertysetManager.findPropertyset(UUID.randomUUID());
        JsonFactory jsonFactory = new JsonFactory();;
        JsonPropertysetPersister persister = new JsonPropertysetPersister(jsonFactory);
        File propertysetsFile = folder.newFile("propertyset.json");
        persister.persist(propertysetsFile, propertysetManager);
        String contents = new String(Files.readAllBytes(propertysetsFile.toPath()));
        System.out.println(contents);
    }
}
