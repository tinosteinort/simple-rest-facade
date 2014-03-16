package de.tse.simplerestfacade;

import java.lang.reflect.InvocationHandler;
import java.net.URI;

import de.tse.simplerestfacade.invocation.RestInvocationHandler;

public class JerseyRestFacadeFactory extends RestFacadeFactory {

	private final RestInterfaceValidator interfaceValidator = new DefaultRestInterfaceValidator();
	
	public JerseyRestFacadeFactory(final URI endpoint) {
		super(endpoint);
	}
	
	public JerseyRestFacadeFactory(String endpoint) {
		super(endpoint);
	}

	public <T> T createFacade(final Class<T> facadeClass, final String mediaType) {
		interfaceValidator.validate(facadeClass);
		
		final RestServiceCaller serviceCaller = new JerseyServiceCaller(endpoint);
		final InvocationHandler invocationHandler = new RestInvocationHandler(serviceCaller, mediaType);
		
		return createProxy(facadeClass, invocationHandler);
	}
}
