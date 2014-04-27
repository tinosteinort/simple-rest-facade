package de.tse.simplerestfacade.jersey.collector;

import java.lang.annotation.Annotation;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;


public class PayloadCollector extends AbstractCollector<Integer> {

	public PayloadCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected boolean supportsCaching() {
		return true;
	}
	
	@Override
	protected Integer collect(final MethodCall methodCall) {
		
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
					|| annotation instanceof HeaderParam) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final Integer payloadIndex) {
		final Object payload = payloadIndex == null ? null : methodCall.getArgs()[payloadIndex];
		methodInformation.setPayload(payload);
	}
}
