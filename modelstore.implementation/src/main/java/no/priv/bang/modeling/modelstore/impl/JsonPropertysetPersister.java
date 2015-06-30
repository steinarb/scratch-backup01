package no.priv.bang.modeling.modelstore.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.UUID;

import static no.priv.bang.modeling.modelstore.impl.Values.*;
import no.priv.bang.modeling.modelstore.Propertyset;
import no.priv.bang.modeling.modelstore.PropertysetContext;
import no.priv.bang.modeling.modelstore.PropertysetManager;
import no.priv.bang.modeling.modelstore.Value;
import no.priv.bang.modeling.modelstore.ValueList;
import no.priv.bang.modeling.modelstore.impl.JsonGeneratorWithReferences;
import no.priv.bang.modeling.modelstore.impl.PropertysetImpl;

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

    public void persist(File propertysetsFile, PropertysetContext propertysetContext) throws IOException {
        outputPropertySets(factory, propertysetsFile, propertysetContext.listAllPropertysets());
    }

    public void restore(File propertysetsFile, PropertysetContext propertysetContext) throws JsonParseException, IOException {
        JsonParser parser = factory.createParser(propertysetsFile);
        parseUntilEnd(propertysetContext, parser);
    }

    public void restore(InputStream propertysetsFile, PropertysetContext propertysetContext) throws JsonParseException, IOException {
        JsonParser parser = factory.createParser(propertysetsFile);
        parseUntilEnd(propertysetContext, parser);
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
            Value value = propertyset.getProperty(propertyname);
            outputValue(generator, propertyname, value);
        }

        generator.writeEndObject();
    }

    private void outputValue(JsonGenerator generator, String propertyname, Value value) throws IOException {
        if (value.isId()) {
            generator.writeFieldName(propertyname);
            generator.writeObjectId(value.asId());
        } else if (value.isReference()) {
            generator.writeFieldName(propertyname);
            generator.writeObjectRef(value.asReference().getId());
        } else if (value.isString()) {
            generator.writeStringField(propertyname, value.asString());
        } else if (value.isDouble()) {
            generator.writeNumberField(propertyname, value.asDouble());
        } else if (value.isLong()) {
            generator.writeNumberField(propertyname, value.asLong());
        } else if (value.isBoolean()) {
            generator.writeBooleanField(propertyname, value.asBoolean());
        } else if (value.isComplexProperty()) {
            generator.writeFieldName(propertyname);
            Propertyset complexValue = value.asComplexProperty();
            outputPropertyset(generator, complexValue);
        } else if (value.isList()) {
            ValueList listvalue = value.asList();
            generator.writeFieldName(propertyname);
            outputArray(generator, listvalue);
        }
    }

    private void outputArray(JsonGenerator generator, ValueList listvalue) throws IOException {
        generator.writeStartArray(listvalue.size());
        for (Value listElement : listvalue) {
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

    private void parseUntilEnd(PropertysetContext propertysetContext,
                               JsonParser parser) throws IOException, JsonParseException {
        while (parser.nextToken() != null) {
            JsonToken currentToken = parser.getCurrentToken();
            if (currentToken == JsonToken.START_ARRAY) {
                parseArray(parser, propertysetContext);
            } else if (currentToken == JsonToken.START_OBJECT) {
                parseObject(parser, propertysetContext);
            }
        }
    }

    private Value parseArray(JsonParser parser, PropertysetContext propertysetContext) throws JsonParseException, IOException {
        ValueList propertyList = newList();
        while (parser.nextToken() != JsonToken.END_ARRAY) {
            JsonToken currentToken = parser.getCurrentToken();
            if (currentToken == JsonToken.VALUE_STRING) {
                propertyList.add(toStringValue(parser.getText()));
            } else if (currentToken == JsonToken.VALUE_NUMBER_FLOAT) {
                propertyList.add(toDoubleValue(parser.getDoubleValue()));
            } else if (currentToken == JsonToken.VALUE_NUMBER_INT) {
                propertyList.add(toLongValue(parser.getLongValue()));
            } else if (currentToken == JsonToken.VALUE_TRUE) {
                propertyList.add(toBooleanValue(true));
            } else if (currentToken == JsonToken.VALUE_FALSE) {
                propertyList.add(toBooleanValue(false));
            } else if (currentToken == JsonToken.START_OBJECT) {
                propertyList.add(parseObject(parser, propertysetContext));
            } else if (currentToken == JsonToken.START_ARRAY) {
                propertyList.add(parseArray(parser, propertysetContext));
            }
        }

        return toListValue(propertyList, false);
    }

    private Value parseObject(JsonParser parser, PropertysetContext propertysetContext) throws JsonParseException, IOException {
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
                Propertyset referencedPropertyset = propertysetContext.findPropertyset(refId);

                // Complete the object, by consuming the END_OBJECT before returning
                parser.nextToken();

                // Return with the reference.
                // If this should happen to be a regular object with a field named "ref",
                // then parsing will fail because next token isn't the expected values
                return toReferenceValue(referencedPropertyset);
            }

            if ("id".equals(currentFieldName)) {
                parser.nextToken();
                String idValue = parser.getText();
                UUID id = UUID.fromString(idValue);
                if (propertyset == null) {
                    propertyset = propertysetContext.findPropertyset(id);
                } else {
                    // Need to copy existing properties parsed earlier
                    Propertyset complexvalue = propertyset;
                    propertyset = propertysetContext.findPropertyset(id);
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
                    propertyset.setProperty(currentFieldName, parseObject(parser, propertysetContext));
                } else if (currentToken == JsonToken.START_ARRAY) {
                    propertyset = createPropertysetIfNull(propertyset);
                    propertyset.setProperty(currentFieldName, parseArray(parser, propertysetContext));
                }
            }
        }

        return toComplexValue(createPropertysetIfNull(propertyset), false);
    }

    private Propertyset createPropertysetIfNull(Propertyset propertyset) {
        propertyset = (propertyset != null) ? propertyset : new PropertysetImpl();
        return propertyset;
    }

}
