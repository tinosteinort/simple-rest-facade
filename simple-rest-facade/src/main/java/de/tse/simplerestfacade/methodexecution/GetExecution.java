package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import de.tse.simplerestfacade.ResultConverterResponseHandler;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;

class GetExecution extends HttpMethodExecution {
    
    protected GetExecution(final URI endpoint, final HttpClient httpClient, final MarshallingConfigProvider marshallingConfigProvider) {
        super(endpoint, httpClient, marshallingConfigProvider);
    }

    @Override public Object execute(final HttpClient httpClient, final MethodInformation methodInformation)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        final HttpGet get = new HttpGet(targetUriFrom(methodInformation));
        addHeader(get, methodInformation);
        
        return httpClient.execute(get, new ResultConverterResponseHandler(getUnmarshaller(methodInformation), methodInformation.getReturnType()));
    }
}