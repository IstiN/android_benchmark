package com.epam.benchmark.jackson;

import com.epam.benchmark.IEntity;
import com.epam.benchmark.IParser;
import com.epam.benchmark.jackson.model.FullObject;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;
import java.util.List;

/**
 * @author Egor Makovsky
 */
public class JacksonParser implements IParser {
    private ObjectMapper mapper;

    @Override
    public void init() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public List<IEntity> parse(InputStream inputStream) throws Exception {
        return mapper.readValue(inputStream, FullObject.class).getResponseItems();
    }
}
