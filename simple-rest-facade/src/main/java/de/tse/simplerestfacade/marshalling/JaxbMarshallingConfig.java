package de.tse.simplerestfacade.marshalling;

import javax.ws.rs.core.MediaType;

public class JaxbMarshallingConfig implements MarshallingConfig {

    private final Marshaller marshaller = new JaxbMarshaller();
    private final Unmarshaller unmarshaller = new JaxbUnmarshaller();
    
    @Override
    public boolean supportsMediaType(final String mediaType) {
        return MediaType.APPLICATION_XML.equals(mediaType);
    }

    @Override
    public Marshaller getMarshaller() {
        return marshaller;
    }

    @Override
    public Unmarshaller getUnmarshaller() {
        return unmarshaller;
    }
}
