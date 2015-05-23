package de.tse.simplerestfacade.methodexecution;

import java.net.URI;

import javax.ws.rs.HttpMethod;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

public class MethodExecutionFactory {
    
    private final URI endpoint;
    private final HttpClient httpClient;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;
    
    public MethodExecutionFactory(final URI endpoint, final HttpClient httpClient, final Unmarshaller unmarshaller, final Marshaller marshaller) {
        this.endpoint = endpoint;
        this.httpClient = httpClient;
        this.unmarshaller = unmarshaller;
        this.marshaller = marshaller;
    }
    
    public MethodExecution create(final String httpMethod) {
        switch (httpMethod) {
            case HttpMethod.GET: return new GetExecution(endpoint, httpClient, unmarshaller, marshaller);
            case HttpMethod.POST: return new PostExecution(endpoint, httpClient, unmarshaller, marshaller);
            case HttpMethod.PUT: return new PutExecution(endpoint, httpClient, unmarshaller, marshaller);
            case HttpMethod.DELETE: return new DeleteExecution(endpoint, httpClient, unmarshaller, marshaller);
            default:
                throw new IllegalArgumentException("HttpMethod not supported: " + httpMethod);
        }
    }
}