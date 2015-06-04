package de.tse.simplerestfacade.methodexecution;

import java.net.URI;

import javax.ws.rs.HttpMethod;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.ExceptionHandler;
import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;

public class MethodExecutionFactory {
    
    private final URI endpoint;
    private final HttpClient httpClient;
    private final MarshallingConfigProvider marshallingConfigProvider;
    private final ExceptionHandler exceptionHandler;
    
    public MethodExecutionFactory(final URI endpoint, final HttpClient httpClient, final MarshallingConfigProvider marshallingConfigProvider, final ExceptionHandler exceptionHandler) {
        this.endpoint = endpoint;
        this.httpClient = httpClient;
        this.marshallingConfigProvider = marshallingConfigProvider;
        this.exceptionHandler = exceptionHandler;
    }
    
    public MethodExecution create(final String httpMethod) {
        switch (httpMethod) {
            case HttpMethod.GET: return new GetExecution(endpoint, httpClient, marshallingConfigProvider, exceptionHandler);
            case HttpMethod.POST: return new PostExecution(endpoint, httpClient, marshallingConfigProvider, exceptionHandler);
            case HttpMethod.PUT: return new PutExecution(endpoint, httpClient, marshallingConfigProvider, exceptionHandler);
            case HttpMethod.DELETE: return new DeleteExecution(endpoint, httpClient, marshallingConfigProvider, exceptionHandler);
            default:
                throw new IllegalArgumentException("HttpMethod not supported: " + httpMethod);
        }
    }
}