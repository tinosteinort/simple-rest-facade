package de.tse.simplerestfacade.invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

import de.tse.simplerestfacade.RestServiceCaller;

public class RestInvocationHandler implements InvocationHandler {

	private final RestServiceCaller serviceCaller;
	private final String mediaType;
	
	private final UrlGenerator urlGenerator = new CachedUrlGenerator();
	private final PayloadDetector payloadDetector = new CachedPayloadDetector();
	private final QueryParameterDetector queryParamDetector = new CachedQueryParameterDetector();
	private final HeaderParameterDetector headerParameterDetector = new CachedHeaderParameterDetector();
	
	public RestInvocationHandler(final RestServiceCaller serviceCaller, final String mediaType) {
		this.serviceCaller = serviceCaller;
		this.mediaType = mediaType;
	}
	
	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		
		// TODO 'MethodCacheInfo cacheInfo = methodCache.getCacheInfos(method);' instead of *Generator and *Detector
		// TODO @FromParam, @MatrixParam, @CookieParam
		// TODO QueryParameter in KeyValue umbenennen
		final String methodUrl = urlGenerator.generate(method, args);
		final Object payload = payloadDetector.detectPayload(method, args);
		final List<QueryParameter> queryParams = queryParamDetector.detectQueryParameter(method, args);
		final List<QueryParameter> headerParams = headerParameterDetector.detectHeaderParameter(method, args);
		
		return serviceCaller.callRestService(methodUrl, queryParams, headerParams, mediaType, payload, method.getReturnType());
	}
}
