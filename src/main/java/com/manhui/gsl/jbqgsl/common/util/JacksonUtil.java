package com.manhui.gsl.jbqgsl.common.util;

import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonUtil {
    private static final ObjectMapper MAPPER;
    static {
        MAPPER = new ObjectMapper();
        MAPPER.setSerializationInclusion( Include.NON_NULL );
        MAPPER.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
    }

    public static <T> T toBean( String json, Class<T> clazz ) {
        try {
            return MAPPER.readValue( json, clazz );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public static String toJson( Object obj ) {
        try {
            return MAPPER.writeValueAsString( obj );
        }
        catch ( JsonProcessingException e ) {
            throw new RuntimeException( e );
        }
    }

    public static ObjectMapper getObjectMapper() {
        return MAPPER;
    }
}
