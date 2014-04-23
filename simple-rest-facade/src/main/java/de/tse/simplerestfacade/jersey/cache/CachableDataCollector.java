package de.tse.simplerestfacade.jersey.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class CachableDataCollector {

	private final List<ParameterAnnotationDataCollector> annotationDataCollectors = new ArrayList<>();
	private final List<MethodDataCollector> methodDataCollectors = new ArrayList<>();
	
	public CachableDataCollector() {
		annotationDataCollectors.add(new PayloadIndexDetector());
		annotationDataCollectors.add(new HeaderParameterIndexDetector());
		annotationDataCollectors.add(new QueryParameterIndexDetector());
		methodDataCollectors.add(new UrlTemplateDetector());
	}
	
	public CachableMethodData collectCachableData(final Method method, final Object[] args) {
		
		final CachableMethodData cachableMethodData = new CachableMethodData();
		
		for (MethodDataCollector collector : methodDataCollectors) {
			collector.collectAndApply(method, cachableMethodData);
		}
		
		final Annotation[][] allParameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < allParameterAnnotations.length; i++) {
			
			for (ParameterAnnotationDataCollector collector : annotationDataCollectors) {
				
				final ParameterInfo parameterInfo = new ParameterInfo(i, allParameterAnnotations[i]);
				collector.collectAndApply(parameterInfo, cachableMethodData);
			}
		}
		
		return cachableMethodData;
	}
}