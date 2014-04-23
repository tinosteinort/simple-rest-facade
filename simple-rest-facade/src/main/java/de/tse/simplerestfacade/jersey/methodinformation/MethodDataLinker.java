package de.tse.simplerestfacade.jersey.methodinformation;

import java.lang.reflect.Method;

import de.tse.simplerestfacade.jersey.DefaultMethodInformation;
import de.tse.simplerestfacade.jersey.cache.CachableMethodData;

public class MethodDataLinker implements DynamicDataLinker<DefaultMethodInformation> {
	
	@Override
	public void apply(final DefaultMethodInformation methodInformation, final CachableMethodData methodData, final Method method, final Object[] args, final String mediaType) {
		
		methodInformation.setMethodUrl(methodData.getUrlTemplate());
		methodInformation.setReturnType(method.getReturnType());
		if (methodData.hasPayload()) {
			methodInformation.setPayload(args[methodData.getPayloadIndex()]);
		}
	}
}