package de.tse.simplerestfacade;

import java.io.IOException;
import java.net.URISyntaxException;

import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.methodexecution.MethodExecution;
import de.tse.simplerestfacade.methodexecution.MethodExecutionFactory;

public class DefaultServiceCaller implements RestServiceCaller {

	private final MethodExecutionFactory executionFactory;
	
	public DefaultServiceCaller(final MethodExecutionFactory executionFactory) {
		this.executionFactory = executionFactory;
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