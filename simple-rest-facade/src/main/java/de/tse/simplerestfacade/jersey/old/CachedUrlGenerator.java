package de.tse.simplerestfacade.jersey.old;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.regex.Matcher;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

class CachedUrlGenerator implements UrlGenerator {

	private final Map<Method, String> urlTemplateCache = new HashMap<>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	@Override
	public String generate(final Method method, final Object[] args) {
		final String urlTemplate = createUrlTemplate(method);
		final Map<String, String> pathParamter = detectPathParamter(method, args);
		
		final String methodUrl = createUrlWithParameter(urlTemplate, pathParamter);
		
		return methodUrl;
	}

	private String createUrlTemplate(final Method method) {
		final String cachedUrlTemplate = getUrlTemplateFromCache(method);
		if (cachedUrlTemplate != null) {
			return cachedUrlTemplate;
		}
		
		final String urlTemplate = detectServicePath(method) + detectMethodPath(method);
		putUrlTemplateIntoCache(method, urlTemplate);
		
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

	private String getUrlTemplateFromCache(final Method method) {
		readWriteLock.readLock().lock();
		try {
			return urlTemplateCache.get(method);
		}
		finally {
			readWriteLock.readLock().unlock();
		}
	}
	
	private void putUrlTemplateIntoCache(final Method method, final String url) {
		readWriteLock.writeLock().lock();
		try {
			urlTemplateCache.put(method, url);
		}
		finally {
			readWriteLock.writeLock().unlock();
		}
	}
	
	private Map<String, String> detectPathParamter(final Method method, final Object[] args) {
		if (args == null) {
			return Collections.emptyMap();
		}
		
		final Map<String, String> parameters = new HashMap<String, String>();
		
		final Annotation[][] allParameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < allParameterAnnotations.length; i++) {
			
			final Annotation[] parameterAnnotations = allParameterAnnotations[i];
			for (Annotation parameterAnnotation : parameterAnnotations) {
				
				if (parameterAnnotation instanceof PathParam) {
					final String paramKey = ((PathParam) parameterAnnotation).value();
					final String paramValue = (String) args[i];
					
					parameters.put(paramKey, paramValue);
				}
			}
		}
		
		return parameters;
	}
	
	private String createUrlWithParameter(final String templateUrl, final Map<String, String> parameter) {
		String methodUrl = templateUrl;
		
		for (Map.Entry<String, String> entry : parameter.entrySet()) {
			final String key = "\\{" + entry.getKey() + "\\}";
			final String replacement = Matcher.quoteReplacement(entry.getValue());
			methodUrl = methodUrl.replaceAll(key, replacement);
		}
		
		return methodUrl;
	}
}
