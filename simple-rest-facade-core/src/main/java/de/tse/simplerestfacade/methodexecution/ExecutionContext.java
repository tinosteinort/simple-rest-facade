package de.tse.simplerestfacade.methodexecution;

import java.net.URI;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.ExceptionHandler;
import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;

public class ExecutionContext {
    
    private final URI endpoint;
    private final String mediaType;
    private final HttpClient httpClient;
    private final MarshallingConfigProvider marshallingConfigProvider;
    private final ExceptionHandler exceptionHandler;
    
    public ExecutionContext(final URI endpoint, final String mediaType, final HttpClient httpClient, final MarshallingConfigProvider marshallingConfigProvider, final ExceptionHandler exceptionHandler) {
        this.endpoint = endpoint;
        this.mediaType = mediaType;
        this.httpClient = httpClient;
        this.marshallingConfigProvider = marshallingConfigProvider;
        this.exceptionHandler = exceptionHandler;
    }
    
    public URI getEndpoint() {
        return endpoint;
    }
    public String getMediaType() {
        return mediaType;
    }
    public HttpClient getHttpClient() {
        return httpClient;
    }
    public MarshallingConfigProvider getMarshallingConfigProvider() {
        return marshallingConfigProvider;
    }
    public ExceptionHandler getExceptionHandler() {
        return exceptionHandler;
    }
}