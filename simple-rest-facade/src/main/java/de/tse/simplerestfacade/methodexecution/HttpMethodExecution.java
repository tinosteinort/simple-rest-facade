package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.UriBuilder;

import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.message.AbstractHttpMessage;

import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.MarshallingConfig;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

abstract class HttpMethodExecution implements MethodExecution {

    private final URI endpoint;
    private final HttpClient httpClient;
    private final MarshallingConfig marshallingConfig;

    protected HttpMethodExecution(final URI endpoint, final HttpClient httpClient, final MarshallingConfig marshallingConfig) {
        this.httpClient = httpClient;
        this.endpoint = endpoint;
        this.marshallingConfig = marshallingConfig;
    }
    
    @Override public Object execute(final MethodInformation methodInformation)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        return execute(httpClient, methodInformation);
    }
    
    abstract Object execute(HttpClient httpClient, MethodInformation methodInformation)
            throws URISyntaxException, ClientProtocolException, IOException;
    
    protected URI targetUriFrom(final MethodInformation methodInformation) throws URISyntaxException {
        
        final UriBuilder builder = UriBuilder.fromUri(endpoint);
        builder.path(methodInformation.getMethodUrl()); // builder.buildFromMap(values)
        
        for (KeyValue pair : methodInformation.getQueryParameter()) {
            builder.queryParam(pair.getKey(), pair.getValue());
        }
        
        return builder.build();
    }
    
    protected void addHeader(final AbstractHttpMessage message, final MethodInformation methodInformation) {
        
        message.addHeader(HttpHeaders.ACCEPT, methodInformation.getMediaType());
        
        for (KeyValue pair : methodInformation.getHeaderParameter()) {
            message.addHeader(pair.getKey(), (String) pair.getValue());
        }
    }
    
    protected Marshaller getMarshaller() {
        return marshallingConfig.getMarshaller();
    }
    protected Unmarshaller getUnmarshaller() {
        return marshallingConfig.getUnmarshaller();
    }
}