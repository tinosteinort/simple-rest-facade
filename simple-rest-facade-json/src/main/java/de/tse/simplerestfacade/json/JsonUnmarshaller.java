package de.tse.simplerestfacade.json;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.tse.simplerestfacade.RestClientException;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

public class JsonUnmarshaller implements Unmarshaller {

    private final ObjectMapper mapper;
    
    public JsonUnmarshaller(final ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override public Object unmarshall(final String string, final Class<?> targetClass) {
        
        try {
            return mapper.readValue(string, targetClass);
        }
        catch (IOException ex) {
            throw new RestClientException("Error while marshalling", ex);
        }
    }
}
