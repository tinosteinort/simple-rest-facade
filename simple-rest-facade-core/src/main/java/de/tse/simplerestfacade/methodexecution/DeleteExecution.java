package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;

import de.tse.simplerestfacade.ExceptionHandler;
import de.tse.simplerestfacade.ExecutionContext;
import de.tse.simplerestfacade.ResultConverterResponseHandler;
import de.tse.simplerestfacade.invocation.MethodInformation;

class DeleteExecution extends HttpMethodExecution {
    
    protected DeleteExecution(final ExecutionContext executionContext) {
        super(executionContext);
    }

    @Override public Object execute(final HttpClient httpClient, final MethodInformation methodInformation, final ExceptionHandler exceptionHandler)
            throws URISyntaxException, ClientProtocolException, IOException {
        
        final HttpDelete delete = new HttpDelete(targetUriFrom(methodInformation));
        setHeaders(delete, methodInformation);
        
        return httpClient.execute(delete, new ResultConverterResponseHandler(getUnmarshaller(methodInformation), methodInformation.getReturnType(), exceptionHandler));
    }
}