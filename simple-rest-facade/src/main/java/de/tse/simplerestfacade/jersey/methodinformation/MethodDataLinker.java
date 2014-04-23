package de.tse.simplerestfacade.jersey.methodinformation;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;
import de.tse.simplerestfacade.jersey.cache.CachableMethodData;

public class MethodDataLinker implements DynamicDataLinker<DefaultMethodInformation> {
	
	@Override
	public void apply(final DefaultMethodInformation methodInformation, final CachableMethodData methodData, final MethodCall methodCall, final String mediaType) {
		
		methodInformation.setMethodUrl(methodData.getUrlTemplate());
		methodInformation.setReturnType(methodCall.getMethod().getReturnType());
		if (methodData.hasPayload()) {
			methodInformation.setPayload(methodCall.getArgs()[methodData.getPayloadIndex()]);
		}
	}
}