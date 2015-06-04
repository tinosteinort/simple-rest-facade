package de.tse.simplerestfacade;

import org.apache.http.HttpResponse;

import de.tse.simplerestfacade.marshalling.Unmarshaller;

public interface ExceptionHandler {

    void handleFaultyResponse(HttpResponse response, Unmarshaller unmarshaller);
}
