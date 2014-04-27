package de.tse.simplerestfacade.jersey.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;

public class ReturnTypeCollector extends AbstractCollector<Class<?>> {

	public ReturnTypeCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected Class<?> collect(final MethodCall methodCall) {
		return methodCall.getMethod().getReturnType();
	}

	@Override
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final Class<?> returnType) {
		methodInformation.setReturnType(returnType);
	}
}
