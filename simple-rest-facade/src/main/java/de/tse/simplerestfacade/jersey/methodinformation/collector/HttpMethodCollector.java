package de.tse.simplerestfacade.jersey.methodinformation.collector;

import java.lang.annotation.Annotation;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HEAD;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.methodinformation.DefaultMethodInformation;
import de.tse.simplerestfacade.jersey.methodinformation.cache.DataCache;

public class HttpMethodCollector extends AbstractCollector<String> {

	public HttpMethodCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected String collectData(final MethodCall methodCall) {
		String httpMethod = null;
		
		for (Annotation annotation : getHttpAnnotations(methodCall)) {
			
			final String method = getHttpMethod(annotation);
			if (method != null) {
				httpMethod = method;
				break;
			}
		}
		
		return httpMethod;
	}
	
	private Annotation[] getHttpAnnotations(final MethodCall methodCall) {
		return new Annotation[] {
						methodCall.getMethod().getAnnotation(DELETE.class),
						methodCall.getMethod().getAnnotation(GET.class),
						methodCall.getMethod().getAnnotation(POST.class),
						methodCall.getMethod().getAnnotation(PUT.class),
						methodCall.getMethod().getAnnotation(HEAD.class),
						methodCall.getMethod().getAnnotation(OPTIONS.class)
					};
	}
	
	private String getHttpMethod(final Annotation annotation) {
		final HttpMethod httpAnnotation = (annotation == null ? null : annotation.annotationType().getAnnotation(HttpMethod.class));
		if (httpAnnotation == null) {
			return null;
		}
		return httpAnnotation.value();
	}
	
	@Override
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final String httpMethod) {
		methodInformation.setHttpMethod(httpMethod);
	}
}