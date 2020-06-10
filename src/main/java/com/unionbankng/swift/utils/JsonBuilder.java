package com.unionbankng.swift.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unionbankng.swift.SwiftException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonBuilder {
    public JsonBuilder() {
    }

    public static String generateJson(Object object) throws SwiftException {

        ObjectMapper mapper = new ObjectMapper();
        String s = null;
        try {
            s = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error occured while generating from Json ",e);
            throw new SwiftException(500,e.getMessage());
        }
        return s;
    }

    public static<T> T otherObj(String jsonObj,Class<T> clazz) throws SwiftException {
        ObjectMapper objectMapper = new ObjectMapper();
        if (!Validation.validData(jsonObj)) throw new SwiftException(404,"No record found. Please start process again");
//        objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        try {
            return objectMapper.readValue(jsonObj, clazz);
        } catch (IOException e) {
            log.error("Error occurred while casting json to object",e);
            throw new SwiftException(500,e.getMessage());
        }

    }

}