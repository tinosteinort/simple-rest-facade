package de.tse.simplerestfacade.jersey;

import java.lang.reflect.InvocationHandler;
import java.net.URI;

import de.tse.simplerestfacade.RestFacadeFactory;
import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.RestInterfaceValidator;
import de.tse.simplerestfacade.RestServiceCaller;
import de.tse.simplerestfacade.invocation.RestInvocationHandler;
import de.tse.simplerestfacade.jersey.methodinformation.JerseyMethodInformationDetector;

public class JerseyRestFacadeFactory extends RestFacadeFactory {

	private final RestInterfaceValidator interfaceValidator = new JerseyRestInterfaceValidator();
	
	public JerseyRestFacadeFactory(final URI endpoint) {
		super(endpoint);
	}
	
	public JerseyRestFacadeFactory(final String endpoint) {
		super(endpoint);
	}

	public <T> T createFacade(final Class<T> facadeClass, final String mediaType) {
		interfaceValidator.validate(facadeClass, mediaType);
		
		final RestServiceCaller serviceCaller = new JerseyServiceCaller(endpoint);
		final RestInformationDetector informationDetector = new JerseyMethodInformationDetector();
		
		final InvocationHandler invocationHandler = new RestInvocationHandler(serviceCaller, informationDetector, mediaType);
		
		return createProxy(facadeClass, invocationHandler);
	}
}
