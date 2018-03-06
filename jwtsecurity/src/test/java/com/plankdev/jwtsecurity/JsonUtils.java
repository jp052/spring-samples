package com.plankdev.jwtsecurity;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
    private static ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    public static <T>T jsonStringToModel(String jsonString, Class<T> clazz) throws IOException {
        T model = mapper.readValue(jsonString, clazz);
        return model;
    }

}
