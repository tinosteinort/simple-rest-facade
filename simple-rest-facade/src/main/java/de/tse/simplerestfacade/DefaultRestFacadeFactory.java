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
    private final MarshallingConfigProvider marshallingConfigProvider = new MarshallingConfigProvider();
	
	public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final String defaultMediaType, final boolean validateRest) {
	    this.endpoint = endpoint;
		this.httpClient = httpClient;
		this.defaultMediaType = defaultMediaType;
		this.validator = validateRest ? Optional.of(new DefaultRestInterfaceValidator()) : Optional.empty();
	}
	
    public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final String defaultMediaType) {
        this(endpoint, httpClient, defaultMediaType, DEFAULT_VALIDATE_REST);
    }

    @Override
    public <T> T createFacade(final Class<T> facadeClass) {
        return createFacade(facadeClass, defaultMediaType);
    }
    
    @Override
    public <T> T createFacade(final Class<T> facadeClass, final String mediaType) {

	    validator.ifPresent(validator -> validator.validate(facadeClass, mediaType));
		
	    final MethodExecutionFactory executionFactory = new MethodExecutionFactory(endpoint, httpClient, marshallingConfigProvider);
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
