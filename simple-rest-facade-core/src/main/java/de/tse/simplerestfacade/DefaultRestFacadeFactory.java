package de.tse.simplerestfacade;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.Optional;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.invocation.RestInvocationHandler;
import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;
import de.tse.simplerestfacade.methodexecution.MethodExecutionFactory;
import de.tse.simplerestfacade.methodinformation.MethodInformationDetector;

public class DefaultRestFacadeFactory implements RestFacadeFactory {
	
    public final static boolean DEFAULT_VALIDATE_REST = true;
    
	private final URI endpoint;
	private final HttpClient httpClient;
	private final String defaultMediaType;
	private final Optional<RestInterfaceValidator> validator;
	private final ExceptionHandler defaultExceptionHandler;
    private final MarshallingConfigProvider marshallingConfigProvider = new MarshallingConfigProvider();
	
	public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final String defaultMediaType, final boolean validateRest, final ExceptionHandler exceptionHandler) {
	    this.endpoint = endpoint;
		this.httpClient = httpClient;
		this.defaultMediaType = defaultMediaType;
		this.validator = validateRest ? Optional.of(new DefaultRestInterfaceValidator()) : Optional.empty();
		this.defaultExceptionHandler = exceptionHandler;
	}
	
    public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final String defaultMediaType) {
        this(endpoint, httpClient, defaultMediaType, DEFAULT_VALIDATE_REST, new DefaultRestExceptionHandler());
    }

    @Override
    public <T> T createFacade(final Class<T> facadeClass) {
        return createFacade(facadeClass, defaultMediaType, defaultExceptionHandler);
    }
    
    @Override
    public <T> T createFacade(final Class<T> facadeClass, final String mediaType, final ExceptionHandler exceptionHandler) {

	    validator.ifPresent(validator -> validator.validate(facadeClass, mediaType));
		
	    final ExecutionContext executionContext = new ExecutionContext(endpoint, httpClient, marshallingConfigProvider, exceptionHandler);
	    final MethodExecutionFactory executionFactory = new MethodExecutionFactory(executionContext);
		final RestServiceCaller serviceCaller = new DefaultServiceCaller(executionFactory);
		final RestInformationDetector informationDetector = new MethodInformationDetector();
		
		final InvocationHandler invocationHandler = new RestInvocationHandler(serviceCaller, informationDetector, mediaType);
		
		return createProxy(facadeClass, invocationHandler);
	}
	
	@SuppressWarnings("unchecked")
    protected <T> T createProxy(final Class<T> facadeClass, final InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { facadeClass }, invocationHandler);
    }
}