package de.tse.simplerestfacade;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
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
    
    public ResultConverterResponseHandler(final Unmarshaller unmarshaller, final Class<T> returnType) {
        this.unmarshaller = unmarshaller;
        this.returnType = returnType;
    }
    
    @Override
    public Object handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {

        validateStatusCode(response);
        
        for (Header header : response.getHeaders(HttpHeaders.CONTENT_TYPE)) {
            System.out.println(header.getName() + ": " + header.getValue() + " (" + header.getElements().length + ")");
        }
        final String responseText = EntityUtils.toString(response.getEntity());
        System.out.println(responseText);

        // TODO je nach Header.ACCEPT (json/xml) den richtigen unmarshaller wählen
        return unmarshaller.unmarshall(responseText, returnType);
    }

    private void validateStatusCode(final HttpResponse response) {
        final StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() != HttpStatus.SC_OK) {
            throw new RuntimeException(statusLine.toString());
        }
    }
}