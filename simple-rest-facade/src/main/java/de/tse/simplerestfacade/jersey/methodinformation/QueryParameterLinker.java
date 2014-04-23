package de.tse.simplerestfacade.jersey.methodinformation;

import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;
import de.tse.simplerestfacade.jersey.cache.CachableMethodData;
import de.tse.simplerestfacade.jersey.cache.ParameterCacheInfo;

public class QueryParameterLinker implements DynamicDataLinker<DefaultMethodInformation> {
	
	@Override
	public void apply(final DefaultMethodInformation methodInformation, final CachableMethodData methodData, final MethodCall methodCall, final String mediaType) {
		
		for (ParameterCacheInfo parameterInfo : methodData.getQueryParameters()) {
			
			final Object value = methodCall.getArgs()[parameterInfo.getIndex()];
			final String key = parameterInfo.getParameterKey();
			methodInformation.getQueryParameter().add(new KeyValue(key, value));
		}
	}
}