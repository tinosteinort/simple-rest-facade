package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import de.tse.simplerestfacade.ExceptionHandler;
import de.tse.simplerestfacade.ResultConverterResponseHandler;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;

class PostExecution extends HttpMethodExecution {
    
    protected PostExecution(final URI endpoint, final HttpClient httpClient, final MarshallingConfigProvider marshallingConfigProvider, final ExceptionHandler exceptionHandler) {
        super(endpoint, httpClient, marshallingConfigProvider, exceptionHandler);
    }

    @Override public Object execute(final HttpClient httpClient, final MethodInformation methodInformation, final ExceptionHandler exceptionHandler)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        final HttpPost post = new HttpPost(targetUriFrom(methodInformation));
        setHeaders(post, methodInformation);
        
        if (methodInformation.hasPayload()) {
            post.setEntity(new StringEntity(getMarshaller(methodInformation).marshall(methodInformation.getPayload())));
        }
        
        return httpClient.execute(post, new ResultConverterResponseHandler(getUnmarshaller(methodInformation), methodInformation.getReturnType(), exceptionHandler));
    }
}