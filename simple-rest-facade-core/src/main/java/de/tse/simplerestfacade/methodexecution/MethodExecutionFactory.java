package de.tse.simplerestfacade.methodexecution;

import javax.ws.rs.HttpMethod;


public class MethodExecutionFactory {
    
    private final ExecutionContext executionContext;
    
    public MethodExecutionFactory(final ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }
    
    public MethodExecution create(final String httpMethod) {
        switch (httpMethod) {
            case HttpMethod.GET: return new GetExecution(executionContext);
            case HttpMethod.POST: return new PostExecution(executionContext);
            case HttpMethod.PUT: return new PutExecution(executionContext);
            case HttpMethod.DELETE: return new DeleteExecution(executionContext);
            default:
                throw new IllegalArgumentException("HttpMethod not supported: " + httpMethod);
        }
    }
}