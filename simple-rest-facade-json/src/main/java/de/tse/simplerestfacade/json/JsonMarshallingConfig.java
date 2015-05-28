package de.tse.simplerestfacade.json;

import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.MarshallingConfig;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

public class JsonMarshallingConfig implements MarshallingConfig {

    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;
    
    public JsonMarshallingConfig() {
        final ObjectMapper mapper = new ObjectMapper();
        
        this.marshaller = new JsonMarshaller(mapper);
        this.unmarshaller = new JsonUnmarshaller(mapper);
    }
    
    @Override public boolean supportsMediaType(final String mediaType) {
        return MediaType.APPLICATION_JSON.equals(mediaType);
    }

    @Override public Marshaller getMarshaller() {
        return marshaller;
    }

    @Override public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }
}
