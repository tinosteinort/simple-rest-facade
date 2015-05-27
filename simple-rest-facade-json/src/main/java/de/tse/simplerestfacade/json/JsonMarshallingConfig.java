package de.tse.simplerestfacade.json;

import javax.ws.rs.core.MediaType;

import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.MarshallingConfig;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

public class JsonMarshallingConfig implements MarshallingConfig {

    private final Marshaller marshaller = new JsonMarshaller();
    private final Unmarshaller unmarshaller = new JsonUnmarshaller();
    
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
