package de.tse.simplerestfacade;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import de.tse.simplerestfacade.marshalling.Unmarshaller;

/**
 * Default Implementation of an ExceptioHandler. Throws a RestClientException in Case of Error.
 * 
 * @author Tino
 */
public class DefaultRestExceptionHandler implements ExceptionHandler {

    /**
     * Called when an REST Call was not successful. Throws a RestClientException.
     * 
     * @param response The HttpResponse
     * @param unmarshaller The Unmarshaller used by the REST Facade
     */
    @Override
    public void handleFaultyResponse(final HttpResponse response, final Unmarshaller unmarshaller) {

        // TODO Content Unmarshalling
        final StatusLine statusLine = response.getStatusLine();
        throw new RestClientException(statusLine.toString());
    }

}
