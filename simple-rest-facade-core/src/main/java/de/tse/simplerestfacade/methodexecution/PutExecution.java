package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import de.tse.simplerestfacade.ExceptionHandler;
import de.tse.simplerestfacade.invocation.ResultConverterResponseHandler;
import de.tse.simplerestfacade.methodinformation.MethodInformation;

class PutExecution extends HttpMethodExecution {
    
    protected PutExecution(final ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override public Object execute(final HttpClient httpClient, final MethodInformation methodInformation, final ExceptionHandler exceptionHandler)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        final HttpPut put = new HttpPut(targetUriFrom(methodInformation));
        setHeaders(put, methodInformation);
        
        if (methodInformation.hasPayload()) {
            put.setEntity(new StringEntity(getMarshaller(methodInformation).marshall(methodInformation.getPayload())));
        }
        
        return httpClient.execute(put, new ResultConverterResponseHandler(getUnmarshaller(methodInformation), methodInformation.getReturnType(), exceptionHandler));
    }
}