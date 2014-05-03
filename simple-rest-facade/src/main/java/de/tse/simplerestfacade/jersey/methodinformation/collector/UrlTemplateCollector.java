package de.tse.simplerestfacade.jersey.methodinformation.collector;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.methodinformation.DefaultMethodInformation;
import de.tse.simplerestfacade.jersey.methodinformation.cache.DataCache;

public class UrlTemplateCollector extends AbstractCollector<UrlData> {
	
	public UrlTemplateCollector(final DataCache cache) {
		super(cache);
	}

	@Override
	protected UrlData collectData(final MethodCall methodCall) {
		final String urlTemplate = detectUrlTemplate(methodCall);
		final List<ParameterCacheInfo> pathParams = detectPathParams(methodCall);
		return new UrlData(urlTemplate, pathParams);
	}

	private String detectUrlTemplate(final MethodCall methodCall) {
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
	
	private List<ParameterCacheInfo> detectPathParams(final MethodCall methodCall) {
		
		final List<ParameterCacheInfo> pathParameterIndexes = new ArrayList<>();
		
		final Annotation[][] allParameterAnnotations = methodCall.getMethod().getParameterAnnotations();
		for (int parameterIndex = 0; parameterIndex < allParameterAnnotations.length; parameterIndex++) {
			
			for (Annotation parameterAnnotation : allParameterAnnotations[parameterIndex]) {
				
				if (parameterAnnotation instanceof PathParam) {
					final String paramKey = ((PathParam) parameterAnnotation).value();
					pathParameterIndexes.add(new ParameterCacheInfo(parameterIndex, paramKey));
				}
			}
		}
		
		return pathParameterIndexes;
	}

	@Override
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final UrlData urlData) {
		
		String methodUrl = urlData.getUrlTemplate();
		
		for (ParameterCacheInfo pathParameter : urlData.getPathParams()) {
			final String key = "\\{" + pathParameter.getParameterKey() + "\\}";
			final Object value = methodCall.getArgs()[pathParameter.getIndex()];
			methodUrl = methodUrl.replace(key, value == null ? "" : String.valueOf(value));
		}
		
		methodInformation.setMethodUrl(methodUrl);
	}
}

class UrlData {
	
	private final String urlTemplate;
	private final List<ParameterCacheInfo> pathParams = new ArrayList<>();
	
	public UrlData(final String urlTemplate, final List<ParameterCacheInfo> pathParams) {
		this.urlTemplate = urlTemplate;
		this.pathParams.addAll(pathParams);
	}
	
	public String getUrlTemplate() {
		return urlTemplate;
	}
	public List<ParameterCacheInfo> getPathParams() {
		return pathParams;
	}
}