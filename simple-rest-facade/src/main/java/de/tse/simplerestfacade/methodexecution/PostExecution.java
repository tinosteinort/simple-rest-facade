package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import de.tse.simplerestfacade.ResultConverterResponseHandler;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

class PostExecution extends HttpMethodExecution {
    
    protected PostExecution(final URI endpoint, final HttpClient httpClient, final Unmarshaller unmarshaller, final Marshaller marshaller) {
        super(endpoint, httpClient, unmarshaller, marshaller);
    }

    @Override public Object execute(final HttpClient httpClient, final MethodInformation methodInformation)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        final HttpPost post = new HttpPost(targetUriFrom(methodInformation));
        addHeader(post, methodInformation);
        
        // TODO je nach Header.ACCEPT (json/xml) den richtigen marshaller wählen
        post.setEntity(new StringEntity(marshaller.marshall(methodInformation.getPayload())));
        
        return httpClient.execute(post, new ResultConverterResponseHandler(unmarshaller, methodInformation.getReturnType()));
    }
}