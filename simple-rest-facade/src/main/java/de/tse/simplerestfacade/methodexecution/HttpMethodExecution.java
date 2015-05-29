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
import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

abstract class HttpMethodExecution implements MethodExecution {

    private final URI endpoint;
    private final HttpClient httpClient;
    private final MarshallingConfigProvider marshallingConfigProvider;

    protected HttpMethodExecution(final URI endpoint, final HttpClient httpClient, final MarshallingConfigProvider marshallingConfigProvider) {
        this.httpClient = httpClient;
        this.endpoint = endpoint;
        this.marshallingConfigProvider = marshallingConfigProvider;
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
    
    protected void setHeaders(final AbstractHttpMessage message, final MethodInformation methodInformation) {
        
        message.addHeader(HttpHeaders.ACCEPT, methodInformation.getMediaType());
        message.addHeader(HttpHeaders.CONTENT_TYPE, methodInformation.getMediaType());
        
        for (KeyValue pair : methodInformation.getHeaderParameter()) {
            message.addHeader(pair.getKey(), (String) pair.getValue());
        }
    }
    
    protected Marshaller getMarshaller(final MethodInformation methodInformation) {
        return marshallingConfigProvider.getMarshaller(methodInformation.getMediaType());
    }
    protected Unmarshaller getUnmarshaller(final MethodInformation methodInformation) {
        return marshallingConfigProvider.getUnmarshaller(methodInformation.getMediaType());
    }
}