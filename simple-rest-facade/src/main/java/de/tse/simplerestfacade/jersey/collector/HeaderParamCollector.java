package de.tse.simplerestfacade.jersey.collector;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.HeaderParam;

import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;

class HeaderParamCollector extends AbstractCollector<List<ParameterCacheInfo>> {

	public HeaderParamCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected List<ParameterCacheInfo> collectData(final MethodCall methodCall) {
		
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
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final List<ParameterCacheInfo> headerParameterIndexes) {
		
		for (ParameterCacheInfo parameterInfo : headerParameterIndexes) {
			
			final Object value = methodCall.getArgs()[parameterInfo.getIndex()];
			final String key = parameterInfo.getParameterKey();
			methodInformation.getHeaderParameter().add(new KeyValue(key, value));
		}
	}
}