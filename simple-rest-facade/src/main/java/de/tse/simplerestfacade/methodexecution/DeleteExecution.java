package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;

import de.tse.simplerestfacade.ResultConverterResponseHandler;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

class DeleteExecution extends HttpMethodExecution {
    
    protected DeleteExecution(final URI endpoint, final HttpClient httpClient, final Unmarshaller unmarshaller, final Marshaller marshaller) {
        super(endpoint, httpClient, unmarshaller, marshaller);
    }

    @Override public Object execute(final HttpClient httpClient, final MethodInformation methodInformation)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        final HttpDelete delete = new HttpDelete(targetUriFrom(methodInformation));
        addHeader(delete, methodInformation);
        
        return httpClient.execute(delete, new ResultConverterResponseHandler(unmarshaller, methodInformation.getReturnType()));
    }
}