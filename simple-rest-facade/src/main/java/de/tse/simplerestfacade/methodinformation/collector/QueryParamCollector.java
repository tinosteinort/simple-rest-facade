package de.tse.simplerestfacade.methodinformation.collector;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformationBuilder;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;

public class QueryParamCollector extends CachableCollector<List<ParameterCacheInfo>> {

	public QueryParamCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected List<ParameterCacheInfo> collectCachableData(final MethodCall methodCall) {
		
		final List<ParameterCacheInfo> queryParameterIndexes = new ArrayList<>();
		
		final Annotation[][] allParameterAnnotations = methodCall.getMethod().getParameterAnnotations();
		for (int parameterIndex = 0; parameterIndex < allParameterAnnotations.length; parameterIndex++) {
			
			for (Annotation parameterAnnotation : allParameterAnnotations[parameterIndex]) {
				
				if (parameterAnnotation instanceof QueryParam) {
					final String paramKey = ((QueryParam) parameterAnnotation).value();
					queryParameterIndexes.add(new ParameterCacheInfo(parameterIndex, paramKey));
				}
			}
		}
		
		return queryParameterIndexes;
	}
	
	@Override
	public void apply(final MethodCall methodCall, final MethodInformationBuilder builder, final List<ParameterCacheInfo> queryParameterIndexes) {
		
		for (ParameterCacheInfo parameterInfo : queryParameterIndexes) {
			
			final Object value = methodCall.getArgs()[parameterInfo.getIndex()];
			final String key = parameterInfo.getParameterKey();
			builder.withQueryParameter(new KeyValue(key, value));
		}
	}
}