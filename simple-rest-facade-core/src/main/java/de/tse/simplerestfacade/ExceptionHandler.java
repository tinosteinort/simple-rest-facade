package de.tse.simplerestfacade;

import org.apache.http.HttpResponse;

import de.tse.simplerestfacade.marshalling.Unmarshaller;

/**
 * This Interface is used to handle Exception after a REST Call.
 * 
 * @author Tino
 */
public interface ExceptionHandler {

    /**
     * Handles the Response if  an REST Call was not successful.
     * 
     * @param response The HttpResponse
     * @param unmarshaller The Unmarshaller used by the REST Facade
     */
    void handleFaultyResponse(HttpResponse response, Unmarshaller unmarshaller);
}
