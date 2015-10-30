package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.message.AbstractHttpMessage;

import de.tse.simplerestfacade.ExceptionHandler;
import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.Unmarshaller;
import de.tse.simplerestfacade.methodinformation.KeyValue;
import de.tse.simplerestfacade.methodinformation.MethodInformation;

abstract class HttpMethodExecution implements MethodExecution {

    private final ExecutionContext executionContext;

    protected HttpMethodExecution(final ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }
    
    @Override public Object execute(final MethodInformation methodInformation)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        return execute(executionContext.getHttpClient(), methodInformation, executionContext.getExceptionHandler());
    }
    
    abstract Object execute(HttpClient httpClient, MethodInformation methodInformation, ExceptionHandler exceptionHandler)
            throws URISyntaxException, ClientProtocolException, IOException;
    
    protected URI targetUriFrom(final MethodInformation methodInformation) throws URISyntaxException {
        
        final UriBuilder builder = UriBuilder.fromUri(executionContext.getEndpoint());
        builder.path(methodInformation.getMethodUrl()); // builder.buildFromMap(values)
        
        for (KeyValue pair : methodInformation.getQueryParameter()) {
            builder.queryParam(pair.getKey(), pair.getValue());
        }
        
        return builder.build();
    }
    
    protected void setHeaders(final AbstractHttpMessage message, final MethodInformation methodInformation) {
        
        message.addHeader(HttpHeaders.ACCEPT, methodInformation.getMediaType());
        
        if (methodInformation.hasPayload()) {
            message.addHeader(HttpHeaders.CONTENT_TYPE, methodInformation.getMediaType());
        }
        
        for (KeyValue pair : methodInformation.getHeaderParameter()) {
            message.addHeader(pair.getKey(), (String) pair.getValue());
        }
    }
    
    protected Marshaller getMarshaller(final MethodInformation methodInformation) {
        return executionContext.getMarshallingConfigProvider().getMarshaller(methodInformation.getMediaType());
    }
    protected Unmarshaller getUnmarshaller(final MethodInformation methodInformation) {
        return executionContext.getMarshallingConfigProvider().getUnmarshaller(methodInformation.getMediaType());
    }
}