package no.priv.bang.modeling.modelstore.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;

/**
 * A {@link JsonGenerator} that can write object IDs and references.
 *
 * @author Steinar Bang
 *
 */
public class JsonGeneratorWithReferences extends JsonGeneratorDelegate {

    public JsonGeneratorWithReferences(JsonGenerator d) {
        super(d);
    }

    @Override
    public boolean canWriteObjectId() {
        return true;
    }

    @Override
    public void writeObjectId(Object id) throws IOException {
        writeString(id.toString());
    }

    @Override
    public void writeObjectRef(Object id) throws IOException {
    	// "compact" the object reference, even when using pretty printing
    	PrettyPrinter prettyPrinter = getPrettyPrinter();
    	setPrettyPrinter(null);
        writeStartObject();
        writeStringField("ref", id.toString());
        writeEndObject();
        setPrettyPrinter(prettyPrinter);
    }
}
