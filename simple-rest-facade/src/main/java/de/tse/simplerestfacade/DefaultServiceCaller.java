package de.tse.simplerestfacade;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.Unmarshaller;
import de.tse.simplerestfacade.methodexecution.MethodExecution;
import de.tse.simplerestfacade.methodexecution.MethodExecutionFactory;

public class DefaultServiceCaller implements RestServiceCaller {

	private final MethodExecutionFactory executionFactory;
	
	public DefaultServiceCaller(final URI endpoint, final HttpClient httpClient, final Unmarshaller unmarshaller, final Marshaller marshaller) {
		this.executionFactory = new MethodExecutionFactory(endpoint, httpClient, unmarshaller, marshaller);
	}
	
	@Override public Object callRestService(final MethodInformation methodInformation) throws RestClientException {
        try {
            final MethodExecution httpExecution = executionFactory.create(methodInformation.getHttpMethod());
            return httpExecution.execute(methodInformation);
        }
        catch (URISyntaxException | IOException ex) {
            throw new RestClientException(ex);
        }
	}
}