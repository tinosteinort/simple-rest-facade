package de.tse.simplerestfacade.xml;

import javax.ws.rs.core.MediaType;

import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.MarshallingConfig;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

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
