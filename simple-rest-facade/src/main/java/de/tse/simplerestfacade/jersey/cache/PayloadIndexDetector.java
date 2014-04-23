package de.tse.simplerestfacade.jersey.cache;

import java.lang.annotation.Annotation;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

class PayloadIndexDetector implements ParameterAnnotationDataCollector {

	@Override
	public void collectAndApply(final ParameterInfo parameterInfo, final CachableMethodData methodData) {
		
		if (isPayloadParameter(parameterInfo.getAnnotations())) {
			methodData.setPayloadIndex(parameterInfo.getIndex());
		}
	}
	
	private boolean isPayloadParameter(final Annotation[] annotationsOfParameter) {
		for (Annotation parameterAnnotation : annotationsOfParameter) {
			
			if (parameterAnnotation instanceof PathParam
					|| parameterAnnotation instanceof QueryParam
					|| parameterAnnotation instanceof HeaderParam) {
				return false;
			}
		}
		return true;
	}
}