package de.tse.simplerestfacade;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

import de.tse.simplerestfacade.marshalling.Unmarshaller;

public class DefaultRestExceptionHandler implements ExceptionHandler {

    @Override
    public void handleFaultyResponse(final HttpResponse response, final Unmarshaller unmarshaller) {

        // TODO Content Unmarshalling
        final StatusLine statusLine = response.getStatusLine();
        throw new RestClientException(statusLine.toString());
    }

}
