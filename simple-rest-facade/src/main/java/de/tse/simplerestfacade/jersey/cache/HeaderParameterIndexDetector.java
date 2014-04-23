package de.tse.simplerestfacade.jersey.cache;

import java.lang.annotation.Annotation;

import javax.ws.rs.HeaderParam;

class HeaderParameterIndexDetector implements ParameterAnnotationDataCollector {

	@Override
	public void collectAndApply(final ParameterInfo parameterInfo, final CachableMethodData methodData) {

		for (Annotation parameterAnnotation : parameterInfo.getAnnotations()) {

			if (parameterAnnotation instanceof HeaderParam) {
				final String paramKey = ((HeaderParam) parameterAnnotation).value();
				methodData.getHeaderParameters().add(new ParameterCacheInfo(parameterInfo.getIndex(), paramKey));
			}
		}
	}
}