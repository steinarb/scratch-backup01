package no.priv.bang.modeling.modelstore;


import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.nio.file.Files;

import static no.priv.bang.modeling.modelstore.testutils.TestUtils.*;
import no.priv.bang.modeling.modelstore.impl.ModelstoreProvider;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

/**
 * Unit test for the {@link Modelstore} interface and its
 * implementations.
 *
 * @author Steinar Bang
 *
 */
public class ModelstoreTest {
    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    /**
     * Test fetching the default context of a
     * {@link Modelstore}.
     */
    @Test
    public void testGetModelContext() {
        Modelstore propertysetManager = new ModelstoreProvider().get();
        ModelContext context = propertysetManager.getDefaultContext();
        assertNotNull(context);
        assertEquals("Expected the built-in aspects", 6, context.listAllAspects().size());
    }

    /**
     * Test loading a {@link ModelContext} from a stream
     * containing a JSON file.
     */
    @Test
    public void testRestoreModelContext() {
    	Modelstore propertysetManager = new ModelstoreProvider().get();
    	InputStream carsAndBicylesStream = getClass().getResourceAsStream("/json/cars_and_bicycles.json");
    	ModelContext context = propertysetManager.restoreContext(carsAndBicylesStream);

    	assertEquals(9, context.listAllAspects().size());
        assertEquals(9, context.listAllPropertysets().size());
    }

    /**
     * Test saving a {@link ModelContext} to a file stream
     * and then restoring the file into a different context.
     * @throws IOException
     */
    @Test
    public void testPersistRestoreModelContext() throws IOException {
        Modelstore propertysetManager = new ModelstoreProvider().get();
        InputStream carsAndBicylesStream = getClass().getResourceAsStream("/json/cars_and_bicycles.json");
        ModelContext context1 = propertysetManager.restoreContext(carsAndBicylesStream);

        File saveFile = folder.newFile();
        OutputStream saveStream = Files.newOutputStream(saveFile.toPath());
        propertysetManager.persistContext(saveStream, context1);

        InputStream loadStream = Files.newInputStream(saveFile.toPath());
        ModelContext context2 = propertysetManager.restoreContext(loadStream);

        compareAllPropertysets(context1, context2);
    }

    /**
     * Test saving a {@link ModelContext} to a {@link PipedOutputStream}
     * and then restoring the context from a {@link PipedInputStream}.
     *
     * Have to write to the pipe from a different thread than the reader,
     * or else it will deadlock.
     *
     * @throws IOException
     */
    @Test
    public void testPersistRestoreModelContextUsingPipedStreams() throws IOException {
        final Modelstore propertysetManager = new ModelstoreProvider().get();
        InputStream carsAndBicylesStream = getClass().getResourceAsStream("/json/cars_and_bicycles.json");
        final ModelContext context1 = propertysetManager.restoreContext(carsAndBicylesStream);

        PipedInputStream loadStream = new PipedInputStream();
        final OutputStream saveStream = new PipedOutputStream(loadStream);
        new Thread(new Runnable() {
                public void run() {
                    propertysetManager.persistContext(saveStream, context1);
                }
            }).start();

        ModelContext context2 = propertysetManager.restoreContext(loadStream);

        compareAllPropertysets(context1, context2);
    }

}
