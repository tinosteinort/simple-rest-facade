package de.tse.simplerestfacade;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.Optional;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.invocation.RestInvocationHandler;
import de.tse.simplerestfacade.marshalling.MarshallingConfig;
import de.tse.simplerestfacade.methodinformation.MethodInformationDetector;

public class DefaultRestFacadeFactory implements RestFacadeFactory {
	
    public final static boolean DEFAULT_VALIDATE_REST = true;
    
	private final URI endpoint;
	private final HttpClient httpClient;
	private final Optional<RestInterfaceValidator> validator;
    private final MarshallingConfig marshallingConfig;
	
	public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final boolean validateRest, final MarshallingConfig marshallingConfig) {
	    this.endpoint = endpoint;
		this.httpClient = httpClient;
		this.validator = validateRest ? Optional.of(new DefaultRestInterfaceValidator()) : Optional.empty();
		this.marshallingConfig = marshallingConfig;
	}
	
    public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final MarshallingConfig marshallingConfig) {
        this(endpoint, httpClient, DEFAULT_VALIDATE_REST, marshallingConfig);
    }

    @Override
    public <T> T createFacade(final Class<T> facadeClass, final String mediaType) {

	    validator.ifPresent(validator -> validator.validate(facadeClass, mediaType));
		
		final RestServiceCaller serviceCaller = new DefaultServiceCaller(endpoint, httpClient, marshallingConfig);
		final RestInformationDetector informationDetector = new MethodInformationDetector();
		
		final InvocationHandler invocationHandler = new RestInvocationHandler(serviceCaller, informationDetector, mediaType);
		
		return createProxy(facadeClass, invocationHandler);
	}
	
	@SuppressWarnings("unchecked")
    protected <T> T createProxy(final Class<T> facadeClass, final InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { facadeClass }, invocationHandler);
    }
}
