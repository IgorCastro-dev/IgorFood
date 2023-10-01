package com.igorfood.core.jackson;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
public class PageJsonSerializer extends JsonSerializer<Page<?>> {

    @Override
    public void serialize(Page<?> objects, JsonGenerator jGen, SerializerProvider serializerProvider) throws IOException {
        jGen.writeStartObject();
        jGen.writeObjectField("content:",objects.getContent());
        jGen.writeNumberField("size:",objects.getSize());
        jGen.writeNumberField("totalElements:",objects.getTotalElements());
        jGen.writeNumberField("totalPage:",objects.getTotalPages());
        jGen.writeNumberField("number:",objects.getNumber());
        jGen.writeEndObject();
    }
}
