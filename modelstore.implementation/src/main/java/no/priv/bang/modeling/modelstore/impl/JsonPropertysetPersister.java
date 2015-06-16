package no.priv.bang.modeling.modelstore.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;

import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetManager;
import no.priv.bang.modeling.modelstore.Propertyvalue;
import no.priv.bang.modeling.modelstore.PropertyvalueList;
import no.priv.bang.modeling.modelstore.impl.BooleanPropertyvalue;
import no.priv.bang.modeling.modelstore.impl.ComplexPropertyvalue;
import no.priv.bang.modeling.modelstore.impl.DoublePropertyvalue;
import no.priv.bang.modeling.modelstore.impl.JsonGeneratorWithReferences;
import no.priv.bang.modeling.modelstore.impl.ListPropertyvalue;
import no.priv.bang.modeling.modelstore.impl.LongPropertyvalue;
import no.priv.bang.modeling.modelstore.impl.PropertysetImpl;
import no.priv.bang.modeling.modelstore.impl.PropertyvalueArrayList;
import no.priv.bang.modeling.modelstore.impl.ReferencePropertyvalue;
import no.priv.bang.modeling.modelstore.impl.StringPropertyvalue;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * This persist and restore the contents of a {@link PropertysetManager} to/from a
 * JSON {@link File}.
 *
 * @author Steinar Bang
 *
 */
public class JsonPropertysetPersister {

    private JsonFactory factory;

    public JsonPropertysetPersister(JsonFactory factory) {
        this.factory = factory;
    }

    public void persist(File propertysetsFile, PropertysetManager propertysetManager) throws IOException {
        outputPropertySets(factory, propertysetsFile, propertysetManager.listAllPropertysets());
    }

    public void restore(File propertysetsFile, PropertysetManager propertysetManager) throws JsonParseException, IOException {
        restore(factory, propertysetsFile, propertysetManager);
    }

    private void outputPropertySets(JsonFactory jsonFactory, File propertysetsFile, Collection<Propertyset> propertysets) throws IOException {
        JsonGenerator generator = new JsonGeneratorWithReferences(jsonFactory.createGenerator(propertysetsFile, JsonEncoding.UTF8));
        if (generator.canWriteObjectId()) {
            generator = new JsonGeneratorWithReferences(generator);
        }

        generator.useDefaultPrettyPrinter();
        generator.writeStartArray();
        for (Propertyset propertyset : propertysets) {
            outputPropertyset(generator, propertyset);
        }

        generator.writeEndArray();
        generator.close();
    }

    private void outputPropertyset(JsonGenerator generator, Propertyset propertyset) throws IOException {
        generator.writeStartObject();
        propertyset.getPropertynames();
        Collection<String> propertynames = propertyset.getPropertynames();
        for (String propertyname : propertynames) {
            Propertyvalue propertyvalue = propertyset.getProperty(propertyname);
            outputPropertyvalue(generator, propertyname, propertyvalue);
        }

        generator.writeEndObject();
    }

    private void outputPropertyvalue(JsonGenerator generator, String propertyname, Propertyvalue propertyvalue) throws IOException {
        if (propertyvalue.isId()) {
            generator.writeFieldName(propertyname);
            generator.writeObjectId(propertyvalue.asId());
        } else if (propertyvalue.isReference()) {
            generator.writeFieldName(propertyname);
            generator.writeObjectRef(propertyvalue.asReference().getId());
        } else if (propertyvalue.isString()) {
            generator.writeStringField(propertyname, propertyvalue.asString());
        } else if (propertyvalue.isDouble()) {
            generator.writeNumberField(propertyname, propertyvalue.asDouble());
        } else if (propertyvalue.isLong()) {
            generator.writeNumberField(propertyname, propertyvalue.asLong());
        } else if (propertyvalue.isBoolean()) {
            generator.writeBooleanField(propertyname, propertyvalue.asBoolean());
        } else if (propertyvalue.isComplexProperty()) {
            generator.writeFieldName(propertyname);
            Propertyset complexPropertyvalue = propertyvalue.asComplexProperty();
            outputPropertyset(generator, complexPropertyvalue);
        } else if (propertyvalue.isList()) {
            PropertyvalueList listvalue = propertyvalue.asList();
            generator.writeFieldName(propertyname);
            outputArray(generator, listvalue);
        }
    }

    private void outputArray(JsonGenerator generator, PropertyvalueList listvalue) throws IOException {
        generator.writeStartArray(listvalue.size());
        for (Propertyvalue listElement : listvalue) {
            if (listElement.isReference()) {
                generator.writeObjectRef(listElement.asReference().getId());
            } else if (listElement.isString()) {
                generator.writeString(listElement.asString());
            } else if (listElement.isDouble()) {
                generator.writeNumber(listElement.asDouble());
            } else if (listElement.isLong()) {
                generator.writeNumber(listElement.asLong());
            } else if (listElement.isBoolean()) {
                generator.writeBoolean(listElement.asBoolean());
            } else if (listElement.isComplexProperty()) {
                outputPropertyset(generator, listElement.asComplexProperty());
            } else if (listElement.isList()) {
                outputArray(generator, listElement.asList());
            }
        }

        generator.writeEndArray();
    }

    private void restore(JsonFactory jsonFactory, File propertysetsFile, PropertysetManager propertysetManager) throws IOException, JsonParseException {
        JsonParser parser = jsonFactory.createParser(propertysetsFile);
        while (parser.nextToken() != null) {
            JsonToken currentToken = parser.getCurrentToken();
            if (currentToken == JsonToken.START_ARRAY) {
                parseArray(parser, propertysetManager);
            } else if (currentToken == JsonToken.START_OBJECT) {
                parseObject(parser, propertysetManager);
            }
        }
    }

    private Propertyvalue parseArray(JsonParser parser, PropertysetManager propertysetManager) throws JsonParseException, IOException {
        PropertyvalueList propertyList = new PropertyvalueArrayList();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            JsonToken currentToken = parser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_STRING) {
                propertyList.add(new StringPropertyvalue(parser.getText()));
            } else if (currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
                propertyList.add(new DoublePropertyvalue(parser.getDoubleValue()));
            } else if (currentToken == JsonToken.VALUE_NUMBER_INT) {
                propertyList.add(new LongPropertyvalue(parser.getLongValue()));
            } else if (currentToken == JsonToken.VALUE_TRUE) {
                propertyList.add(new BooleanPropertyvalue(true));
            } else if (currentToken == JsonToken.VALUE_FALSE) {
                propertyList.add(new BooleanPropertyvalue(false));
            } else if (currentToken == JsonToken.START_OBJECT) {
                propertyList.add(parseObject(parser, propertysetManager));
            } else if (currentToken == JsonToken.START_ARRAY) {
                propertyList.add(parseArray(parser, propertysetManager));
            }
        }

        return new ListPropertyvalue(propertyList);
    }

    private Propertyvalue parseObject(JsonParser parser, PropertysetManager propertysetManager) throws JsonParseException, IOException {
        Propertyset propertyset = null;
        while (parser.nextToken() != JsonToken.END_OBJECT) {
            String currentFieldName = parser.getCurrentName();
            if ("ref".equals(currentFieldName)) {
                // This is an object reference, rather than an object
                parser.nextToken();
                String refValue = parser.getText();
                UUID refId = UUID.fromString(refValue);

                // Note: This will create an empty placeholder if the referenced object
                // hasn't been parsed yet.
                // When the referenced object is parsed it will retrieve the same
                // placeholder and start filling in its properties.
                Propertyset referencedPropertyset = propertysetManager.findPropertyset(refId);

                // Complete the object, by consuming the END_OBJECT before returning
                parser.nextToken();

                // Return with the reference.
                // If this should happen to be a regular object with a field named "ref",
                // then parsing will fail because next token isn't the expected values
                return new ReferencePropertyvalue(referencedPropertyset);
            }

            if ("id".equals(currentFieldName)) {
                parser.nextToken();
                String idValue = parser.getText();
                UUID id = UUID.fromString(idValue);
                if (propertyset == null) {
                    propertyset = propertysetManager.findPropertyset(id);
                } else {
                    // Need to copy existing properties parsed earlier
                    Propertyset complexvalue = propertyset;
                    propertyset = propertysetManager.findPropertyset(id);
                    for (String propertyname : complexvalue.getPropertynames()) {
                        propertyset.setProperty(propertyname, complexvalue.getProperty(propertyname));
                    }
                }
            } else {
                // Parsing all ordinary properties of a propertyset
                parser.nextToken();
                JsonToken currentToken = parser.getCurrentToken();
                if (currentToken == JsonToken.VALUE_STRING) {
                    propertyset = createPropertysetIfNull(propertyset);
                    propertyset.setStringProperty(currentFieldName, parser.getText());
                } else if (currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
                    propertyset = createPropertysetIfNull(propertyset);
                    propertyset.setDoubleProperty(currentFieldName, parser.getDoubleValue());
                } else if (currentToken == JsonToken.VALUE_NUMBER_INT) {
                    propertyset = createPropertysetIfNull(propertyset);
                    propertyset.setLongProperty(currentFieldName, parser.getLongValue());
                } else if (currentToken == JsonToken.VALUE_TRUE) {
                    propertyset = createPropertysetIfNull(propertyset);
                    propertyset.setBooleanProperty(currentFieldName, true);
                } else if (currentToken == JsonToken.VALUE_FALSE) {
                    propertyset = createPropertysetIfNull(propertyset);
                    propertyset.setBooleanProperty(currentFieldName, false);
                } else if (currentToken == JsonToken.START_OBJECT) {
                    propertyset = createPropertysetIfNull(propertyset);
                    propertyset.setProperty(currentFieldName, parseObject(parser, propertysetManager));
                } else if (currentToken == JsonToken.START_ARRAY) {
                    propertyset = createPropertysetIfNull(propertyset);
                    propertyset.setProperty(currentFieldName, parseArray(parser, propertysetManager));
                }
            }
        }

        return new ComplexPropertyvalue(createPropertysetIfNull(propertyset));
    }

    private Propertyset createPropertysetIfNull(Propertyset propertyset) {
        propertyset = (propertyset != null) ? propertyset : new PropertysetImpl();
        return propertyset;
    }

}
