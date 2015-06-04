package de.tse.simplerestfacade;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import de.tse.simplerestfacade.marshalling.Unmarshaller;

public class ResultConverterResponseHandler<T> implements ResponseHandler<Object> {
    
    private final Unmarshaller unmarshaller;
    private final Class<T> returnType;
    private final ExceptionHandler exceptionHandler;
    
    public ResultConverterResponseHandler(final Unmarshaller unmarshaller, final Class<T> returnType, final ExceptionHandler exceptionHandler) {
        this.unmarshaller = unmarshaller;
        this.returnType = returnType;
        this.exceptionHandler = exceptionHandler;
    }
    
    @Override
    public Object handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {

        validateStatusCode(response);
        
        Object result = null;
        
        if (response.getEntity() != null) {

            final String content = EntityUtils.toString(response.getEntity());
            result = unmarshaller.unmarshall(content, returnType);
        }
        
        return result;
    }

    private void validateStatusCode(final HttpResponse response) {
        if (!requestWasSuccessful(response.getStatusLine().getStatusCode())) {
            exceptionHandler.handleFaultyResponse(response, unmarshaller);
        }
    }
    
    private boolean requestWasSuccessful(final int statusCode) {
        return statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES;
    }
}