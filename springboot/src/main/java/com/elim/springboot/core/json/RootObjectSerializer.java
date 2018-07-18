package com.elim.springboot.core.json;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class RootObjectSerializer extends JsonObjectSerializer<RootObject> {

    @Override
    protected void serializeObject(RootObject value, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {
        provider.defaultSerializeField("id", value.getId(), jgen);
        provider.defaultSerializeField("code", value.getCode(), jgen);
        List<Shape> shapes = value.getShapes();
        if (shapes != null && !shapes.isEmpty()) {
            for (Shape shape : shapes) {
                provider.defaultSerializeField(shape.getName(), shape, jgen);
            }
        }
    }

}
