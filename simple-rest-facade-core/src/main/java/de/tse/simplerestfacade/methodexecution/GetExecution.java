package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import de.tse.simplerestfacade.ExceptionHandler;
import de.tse.simplerestfacade.ExecutionContext;
import de.tse.simplerestfacade.ResultConverterResponseHandler;
import de.tse.simplerestfacade.invocation.MethodInformation;

class GetExecution extends HttpMethodExecution {
    
    protected GetExecution(final ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override public Object execute(final HttpClient httpClient, final MethodInformation methodInformation, final ExceptionHandler exceptionHandler)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        final HttpGet get = new HttpGet(targetUriFrom(methodInformation));
        setHeaders(get, methodInformation);
        
        return httpClient.execute(get, new ResultConverterResponseHandler(getUnmarshaller(methodInformation), methodInformation.getReturnType(), exceptionHandler));
    }
}