package de.tse.simplerestfacade.methodinformation.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.methodinformation.DefaultMethodInformation;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;

public class ReturnTypeCollector extends AbstractCollector<Class<?>> {

	public ReturnTypeCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected Class<?> collectData(final MethodCall methodCall) {
		return methodCall.getMethod().getReturnType();
	}

	@Override
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final Class<?> returnType) {
		methodInformation.setReturnType(returnType);
	}
}
