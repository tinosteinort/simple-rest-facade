package de.tse.simplerestfacade.methodinformation.collector;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.HeaderParam;

import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformationBuilder;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;

public class HeaderParamCollector extends CachableCollector<List<ParameterCacheInfo>> {

	public HeaderParamCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected List<ParameterCacheInfo> collectCachableData(final MethodCall methodCall) {
		
		final List<ParameterCacheInfo> headerParameterIndexes = new ArrayList<>();
		
		final Annotation[][] allParameterAnnotations = methodCall.getMethod().getParameterAnnotations();
		for (int parameterIndex = 0; parameterIndex < allParameterAnnotations.length; parameterIndex++) {
			
			for (Annotation parameterAnnotation : allParameterAnnotations[parameterIndex]) {
				
				if (parameterAnnotation instanceof HeaderParam) {
					final String paramKey = ((HeaderParam) parameterAnnotation).value();
					headerParameterIndexes.add(new ParameterCacheInfo(parameterIndex, paramKey));
				}
			}
		}
		
		return headerParameterIndexes;
	}
	
	@Override
	public void apply(final MethodCall methodCall, final MethodInformationBuilder builder, final List<ParameterCacheInfo> headerParameterIndexes) {
		
		for (ParameterCacheInfo parameterInfo : headerParameterIndexes) {
			
			final Object value = methodCall.getArgs()[parameterInfo.getIndex()];
			final String key = parameterInfo.getParameterKey();
			builder.withHeaderParameter(new KeyValue(key, value));
		}
	}
}