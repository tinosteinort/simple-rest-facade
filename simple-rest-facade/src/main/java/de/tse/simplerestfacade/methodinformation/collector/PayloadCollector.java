package de.tse.simplerestfacade.methodinformation.collector;

import java.lang.annotation.Annotation;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformationBuilder;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;


public class PayloadCollector extends AbstractCollector<Integer> {

	public PayloadCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected Integer collectData(final MethodCall methodCall) {
		
		final Annotation[][] allParameterAnnotations = methodCall.getMethod().getParameterAnnotations();
		for (int parameterIndex = 0; parameterIndex < allParameterAnnotations.length; parameterIndex++) {
			
			if (isPayloadParameter(allParameterAnnotations[parameterIndex])) {
				return parameterIndex;
			}
		}
		return null;
	}
	
	private boolean isPayloadParameter(final Annotation[] annotationsOfParameter) {
		for (Annotation annotation : annotationsOfParameter) {
			
			if (annotation instanceof PathParam
					|| annotation instanceof QueryParam
					|| annotation instanceof HeaderParam
					|| annotation instanceof MatrixParam) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void apply(final MethodCall methodCall, final MethodInformationBuilder builder, final Integer payloadIndex) {
		final Object payload = payloadIndex == null ? null : methodCall.getArgs()[payloadIndex];
		builder.withPayload(payload);
	}
}
