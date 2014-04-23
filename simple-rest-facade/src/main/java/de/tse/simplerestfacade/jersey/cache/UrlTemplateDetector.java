package de.tse.simplerestfacade.jersey.cache;

import java.lang.reflect.Method;

import javax.ws.rs.Path;

class UrlTemplateDetector implements MethodDataCollector {

	@Override
	public void collectAndApply(final Method method, final CachableMethodData methodData) {
		
		final String urlTemplate = detectServicePath(method) + detectMethodPath(method);
		methodData.setUrlTemplate(urlTemplate);
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
}