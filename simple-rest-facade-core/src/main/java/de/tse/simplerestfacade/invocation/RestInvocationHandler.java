package de.tse.simplerestfacade.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.buildin.DefaultServiceCaller;
import de.tse.simplerestfacade.buildin.RestServiceCaller;
import de.tse.simplerestfacade.methodexecution.ExecutionContext;
import de.tse.simplerestfacade.methodexecution.MethodExecutionFactory;
import de.tse.simplerestfacade.methodinformation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformation;
import de.tse.simplerestfacade.methodinformation.MethodInformationDetector;

public class RestInvocationHandler implements InvocationHandler {

    private final ExecutionContext executionContext;
	private final RestServiceCaller serviceCaller;
	private final RestInformationDetector informationDetector;
	
	public RestInvocationHandler(final ExecutionContext executionContext) {
	    
	    this.executionContext = executionContext;
		this.serviceCaller = new DefaultServiceCaller(new MethodExecutionFactory(this.executionContext));
		this.informationDetector = new MethodInformationDetector();
	}
	
	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		
		final MethodCall call = new MethodCall(method, args, executionContext.getMediaType());
		final MethodInformation information = informationDetector.detectRestInformations(call);
		
		return serviceCaller.callRestService(information);
	}
}
