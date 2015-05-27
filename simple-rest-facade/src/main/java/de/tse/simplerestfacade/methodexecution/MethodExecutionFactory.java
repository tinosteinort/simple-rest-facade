package de.tse.simplerestfacade.methodexecution;

import java.net.URI;

import javax.ws.rs.HttpMethod;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;

public class MethodExecutionFactory {
    
    private final URI endpoint;
    private final HttpClient httpClient;
    private final MarshallingConfigProvider marshallingConfigProvider;
    
    public MethodExecutionFactory(final URI endpoint, final HttpClient httpClient, final MarshallingConfigProvider marshallingConfigProvider) {
        this.endpoint = endpoint;
        this.httpClient = httpClient;
        this.marshallingConfigProvider = marshallingConfigProvider;
    }
    
    public MethodExecution create(final String httpMethod) {
        switch (httpMethod) {
            case HttpMethod.GET: return new GetExecution(endpoint, httpClient, marshallingConfigProvider);
            case HttpMethod.POST: return new PostExecution(endpoint, httpClient, marshallingConfigProvider);
            case HttpMethod.PUT: return new PutExecution(endpoint, httpClient, marshallingConfigProvider);
            case HttpMethod.DELETE: return new DeleteExecution(endpoint, httpClient, marshallingConfigProvider);
            default:
                throw new IllegalArgumentException("HttpMethod not supported: " + httpMethod);
        }
    }
}