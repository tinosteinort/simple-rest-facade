package de.tse.simplerestfacade.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.tse.simplerestfacade.RestClientException;
import de.tse.simplerestfacade.marshalling.Marshaller;

public class JsonMarshaller implements Marshaller {

    private final ObjectMapper mapper;
    
    public JsonMarshaller(final ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    @Override public String marshall(final Object data) {
        
        try {
            return mapper.writeValueAsString(data);
        }
        catch (JsonProcessingException ex) {
            throw new RestClientException("Error while marshalling", ex);
        }
    }
}
