package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;

import de.tse.simplerestfacade.ResultConverterResponseHandler;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

class PutExecution extends HttpMethodExecution {
    
    protected PutExecution(final URI endpoint, final HttpClient httpClient, final Unmarshaller unmarshaller, final Marshaller marshaller) {
        super(endpoint, httpClient, unmarshaller, marshaller);
    }

    @Override public Object execute(final HttpClient httpClient, final MethodInformation methodInformation)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        final HttpPut put = new HttpPut(targetUriFrom(methodInformation));
        addHeader(put, methodInformation);
        
        return httpClient.execute(put, new ResultConverterResponseHandler(unmarshaller, methodInformation.getReturnType()));
    }
}