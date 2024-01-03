package com.study.hateoas.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(final Errors errors, final JsonGenerator gen, final SerializerProvider serializers) throws IOException {
        gen.writeFieldName("errors");
        gen.writeStartArray();

        errors.getFieldErrors()
            .forEach(e -> {
                try {
                    gen.writeStartObject();
                    gen.writeStringField("field", e.getField());
                    gen.writeStringField("objectName", e.getObjectName());
                    gen.writeStringField("code", e.getCode());
                    gen.writeStringField("defaultMessage", e.getDefaultMessage());

                    final Object rejectedValue = e.getRejectedValue();
                    if (rejectedValue != null) {
                        gen.writeStringField("rejectedValue", rejectedValue.toString());
                    }
                    gen.writeEndObject();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

        errors.getGlobalErrors()
            .forEach(e -> {
                try {
                    gen.writeStartObject();
                    gen.writeStringField("objectName", e.getObjectName());
                    gen.writeStringField("code", e.getCode());
                    gen.writeStringField("defaultMessage", e.getDefaultMessage());
                    gen.writeEndObject();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

        gen.writeEndArray();
    }
}
