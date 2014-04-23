package de.tse.simplerestfacade.jersey.cache;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import de.tse.simplerestfacade.invocation.MethodCall;

class CachableDataCollector {

	private final List<ParameterAnnotationDataCollector> annotationDataCollectors = new ArrayList<>();
	private final List<MethodDataCollector> methodDataCollectors = new ArrayList<>();
	
	public CachableDataCollector() {
		annotationDataCollectors.add(new PayloadIndexDetector());
		annotationDataCollectors.add(new HeaderParameterIndexDetector());
		annotationDataCollectors.add(new QueryParameterIndexDetector());
		methodDataCollectors.add(new UrlTemplateDetector());
	}
	
	public CachableMethodData collectCachableData(final MethodCall methodCall) {
		
		final CachableMethodData cachableMethodData = new CachableMethodData();
		
		for (MethodDataCollector collector : methodDataCollectors) {
			collector.collectAndApply(methodCall.getMethod(), cachableMethodData);
		}
		
		final Annotation[][] allParameterAnnotations = methodCall.getMethod().getParameterAnnotations();
		for (int i = 0; i < allParameterAnnotations.length; i++) {
			
			for (ParameterAnnotationDataCollector collector : annotationDataCollectors) {
				
				final ParameterInfo parameterInfo = new ParameterInfo(i, allParameterAnnotations[i]);
				collector.collectAndApply(parameterInfo, cachableMethodData);
			}
		}
		
		return cachableMethodData;
	}
}