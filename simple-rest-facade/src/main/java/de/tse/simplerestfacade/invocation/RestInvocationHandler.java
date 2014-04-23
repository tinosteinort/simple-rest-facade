package de.tse.simplerestfacade.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.RestServiceCaller;

public class RestInvocationHandler implements InvocationHandler {

	private final RestServiceCaller serviceCaller;
	private final RestInformationDetector informationDetector;
	private final String mediaType;
	
	public RestInvocationHandler(final RestServiceCaller serviceCaller, 
								 final RestInformationDetector restInformationDetector, 
								 final String mediaType) {
		this.serviceCaller = serviceCaller;
		this.informationDetector = restInformationDetector;
		this.mediaType = mediaType;
	}
	
	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		
		final MethodCall call = new MethodCall(method, args);
		final MethodInformation information = informationDetector.detectRestInformations(call, mediaType);
		
		return serviceCaller.callRestService(information);
	}
}
