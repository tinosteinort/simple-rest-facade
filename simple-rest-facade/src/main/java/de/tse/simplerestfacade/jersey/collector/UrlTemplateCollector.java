package de.tse.simplerestfacade.jersey.collector;

import java.lang.reflect.Method;

import javax.ws.rs.Path;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;

public class UrlTemplateCollector extends AbstractCollector<String> {
	
	public UrlTemplateCollector(final DataCache cache) {
		super(cache);
	}

	@Override
	protected String collect(final MethodCall methodCall) {
		final String urlTemplate = detectServicePath(methodCall.getMethod()) + detectMethodPath(methodCall.getMethod());
		return urlTemplate;
	}
	
	private String detectServicePath(final Method method) {
		final Class<?> interfaceClass = method.getDeclaringClass();
		final Path interfacePathAnnotation = interfaceClass.getAnnotation(Path.class);
		if (interfacePathAnnotation != null) {
			return interfacePathAnnotation.value();
		}
		return "";
	}
	
	private String detectMethodPath(final Method method) {
		final Path methodPathAnnotation = method.getAnnotation(Path.class);
		if (methodPathAnnotation != null) {
			return methodPathAnnotation.value();
		}
		return "";
	}

	@Override
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final String urlTemplate) {
		methodInformation.setMethodUrl(urlTemplate);
	}
}
