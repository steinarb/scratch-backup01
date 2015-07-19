package no.priv.bang.modeling.modelstore.impl;

import no.priv.bang.modeling.modelstore.ModelContext;
import static no.priv.bang.modeling.modelstore.impl.ModelContexts.*;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Unit tests for {@link ModelContexts}
 *
 * @author Steinar Bang
 *
 */
public class ModelContextsTest {

    /**
     * Unit test for {@link ModelContexts#findWrappedModelContext(no.priv.bang.modeling.modelstore.ModelContext)}.
     */
    @Test
    public void testFindWrappedModelContext() {
        ModelContext inner = new ModelContextImpl();
        ModelContext context = new ModelContextRecordingMetadata(inner);
        assertSame(inner, findWrappedModelContext(context));
        assertSame(inner, findWrappedModelContext(inner));
    }

}
