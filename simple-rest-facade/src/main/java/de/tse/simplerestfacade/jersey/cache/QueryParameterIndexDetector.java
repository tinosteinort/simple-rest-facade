package de.tse.simplerestfacade.jersey.cache;

import java.lang.annotation.Annotation;

import javax.ws.rs.QueryParam;

class QueryParameterIndexDetector implements ParameterAnnotationDataCollector {

	@Override
	public void collectAndApply(final ParameterInfo parameterInfo, final CachableMethodData methodData) {

		for (Annotation parameterAnnotation : parameterInfo.getAnnotations()) {

			if (parameterAnnotation instanceof QueryParam) {
				final String paramKey = ((QueryParam) parameterAnnotation).value();
				methodData.getQueryParameters().add(new ParameterCacheInfo(parameterInfo.getIndex(), paramKey));
			}
		}
	}
}