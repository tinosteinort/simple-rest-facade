package de.tse.simplerestfacade.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import de.tse.simplerestfacade.DefaultServiceCaller;
import de.tse.simplerestfacade.ExecutionContext;
import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.RestServiceCaller;
import de.tse.simplerestfacade.methodexecution.MethodExecutionFactory;
import de.tse.simplerestfacade.methodinformation.MethodInformationDetector;

public class RestInvocationHandler implements InvocationHandler {

	private final RestServiceCaller serviceCaller;
	private final RestInformationDetector informationDetector;
	private final String mediaType;
	
	public RestInvocationHandler(final ExecutionContext executionContext, final String mediaType) {
	    
		this.serviceCaller = new DefaultServiceCaller(new MethodExecutionFactory(executionContext));
		this.informationDetector = new MethodInformationDetector();
		this.mediaType = mediaType;
	}
	
	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		
		final MethodCall call = new MethodCall(method, args, mediaType);
		final MethodInformation information = informationDetector.detectRestInformations(call);
		
		return serviceCaller.callRestService(information);
	}
}
