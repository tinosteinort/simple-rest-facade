package de.tse.simplerestfacade.marshalling;

public interface Unmarshaller {

    Object unmarshall(String string, Class<?> targetClass);
}
