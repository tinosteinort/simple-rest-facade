package de.tse.simplerestfacade.marshalling;

public interface MarshallingConfig {

    boolean supportsMediaType(String mediaType);
    
    Marshaller getMarshaller();
    Unmarshaller getUnmarshaller();
}
