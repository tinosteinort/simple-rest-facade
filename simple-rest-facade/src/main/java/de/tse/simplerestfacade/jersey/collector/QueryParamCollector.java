package de.tse.simplerestfacade.jersey.collector;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.QueryParam;

import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;
import de.tse.simplerestfacade.jersey.cache.ParameterCacheInfo;

class QueryParamCollector extends AbstractCollector<List<ParameterCacheInfo>> {

	public QueryParamCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected boolean supportsCaching() {
		return true;
	}
	
	@Override
	protected List<ParameterCacheInfo> collect(final MethodCall methodCall, final String mediaType) {
		
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
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final String mediaType, final List<ParameterCacheInfo> queryParameterIndexes) {
		
		for (ParameterCacheInfo parameterInfo : queryParameterIndexes) {
			
			final Object value = methodCall.getArgs()[parameterInfo.getIndex()];
			final String key = parameterInfo.getParameterKey();
			methodInformation.getQueryParameter().add(new KeyValue(key, value));
		}
	}
}