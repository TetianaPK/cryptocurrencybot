package com.phototeca.cryptocurrencybot.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


public final class JsonParserUtil {

    private static ObjectMapper objectMapper;
    private static ObjectWriter objectWriter;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectWriter = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .writer().withDefaultPrettyPrinter();
    }

    private JsonParserUtil() {
        throw new AssertionError("This is a utility class and cannot be instantiated");
    }


    public static String serialize(Object object) {
        try {
            return objectWriter.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Request processing exception " + e.getMessage());
        }
    }

    public static <T> T serialize(Object object, Class<T> type) {
        try {
            return objectMapper.convertValue(object, type);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Request processing exception " + e.getMessage());
        }
    }

    public static <T> T deSerialize(String jsonString, Class<T> type) {
        try {
            return objectMapper.readValue(jsonString, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Request processing exception " + e.getMessage());
        }
    }

    public static <T> T deSerialize(String jsonString, TypeReference<T> valueTypeRef) {
        try {
            return objectMapper.readValue(jsonString, valueTypeRef);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Request processing exception " + e.getMessage());
        }
    }


}
