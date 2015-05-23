package de.tse.simplerestfacade.methodexecution;

import java.net.URI;

import javax.ws.rs.HttpMethod;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.marshalling.MarshallingConfig;

public class MethodExecutionFactory {
    
    private final URI endpoint;
    private final HttpClient httpClient;
    private final MarshallingConfig marshallingConfig;
    
    public MethodExecutionFactory(final URI endpoint, final HttpClient httpClient, final MarshallingConfig marshallingConfig) {
        this.endpoint = endpoint;
        this.httpClient = httpClient;
        this.marshallingConfig = marshallingConfig;
    }
    
    public MethodExecution create(final String httpMethod) {
        switch (httpMethod) {
            case HttpMethod.GET: return new GetExecution(endpoint, httpClient, marshallingConfig);
            case HttpMethod.POST: return new PostExecution(endpoint, httpClient, marshallingConfig);
            case HttpMethod.PUT: return new PutExecution(endpoint, httpClient, marshallingConfig);
            case HttpMethod.DELETE: return new DeleteExecution(endpoint, httpClient, marshallingConfig);
            default:
                throw new IllegalArgumentException("HttpMethod not supported: " + httpMethod);
        }
    }
}