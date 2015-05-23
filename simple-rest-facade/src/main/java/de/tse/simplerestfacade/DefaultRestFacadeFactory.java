package de.tse.simplerestfacade;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.Optional;

import javax.ws.rs.core.MediaType;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.invocation.RestInvocationHandler;
import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.Unmarshaller;
import de.tse.simplerestfacade.methodinformation.MethodInformationDetector;

public class DefaultRestFacadeFactory implements RestFacadeFactory {
	
    public final static boolean DEFAULT_VALIDATE_REST = true;
    
	private final URI endpoint;
	private final HttpClient httpClient;
	private final Optional<RestInterfaceValidator> validator;
    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;
	
	public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final boolean validateRest, final Unmarshaller unmarshaller, final Marshaller marshaller) {
	    this.endpoint = endpoint;
		this.httpClient = httpClient;
		this.validator = validateRest ? Optional.of(new DefaultRestInterfaceValidator()) : Optional.empty();
		this.unmarshaller = unmarshaller;
		this.marshaller = marshaller;
	}
	
    public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final Unmarshaller unmarshaller, final Marshaller marshaller) {
        this(endpoint, httpClient, DEFAULT_VALIDATE_REST, unmarshaller, marshaller);
    }

    @Override
    public <T> T createFacade(final Class<T> facadeClass, final String mediaType) {

	    validator.ifPresent(validator -> validator.validate(facadeClass, mediaType));
		
		final RestServiceCaller serviceCaller = new DefaultServiceCaller(endpoint, httpClient, unmarshaller, marshaller);
		final RestInformationDetector informationDetector = new MethodInformationDetector();
		
		final InvocationHandler invocationHandler = new RestInvocationHandler(serviceCaller, informationDetector, mediaType);
		
		return createProxy(facadeClass, invocationHandler);
	}
	
	@SuppressWarnings("unchecked")
    protected <T> T createProxy(final Class<T> facadeClass, final InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { facadeClass }, invocationHandler);
    }
}
