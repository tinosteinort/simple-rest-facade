package de.tse.simplerestfacade.jersey;

import java.lang.reflect.Method;
import java.util.List;

import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.invocation.KeyValue;

public class JerseyMethodInformationDetector implements RestInformationDetector {

	private final UrlGenerator urlGenerator = new CachedUrlGenerator();
	private final PayloadDetector payloadDetector = new CachedPayloadDetector();
	private final QueryParameterDetector queryParamDetector = new CachedQueryParameterDetector();
	private final HeaderParameterDetector headerParameterDetector = new CachedHeaderParameterDetector();
	
	@Override
	public MethodInformation detectRestInformations(final Method method, final Object[] args, final String mediaType) {
		
		// TODO 'MethodCacheInfo cacheInfo = methodCache.getCacheInfos(method);' instead of *Generator and *Detector
		// TODO @FromParam, @MatrixParam, @CookieParam
		
		final String methodUrl = urlGenerator.generate(method, args);
		final Object payload = payloadDetector.detectPayload(method, args);
		final List<KeyValue> queryParams = queryParamDetector.detectQueryParameter(method, args);
		final List<KeyValue> headerParams = headerParameterDetector.detectHeaderParameter(method, args);
		
		final MethodInformation information = new MethodInformation.Builder()
													.mediaType(mediaType)
													.methodUrl(methodUrl)
													.payload(payload)
													.queryParameter(queryParams)
													.headerParameter(headerParams)
													.returnType(method.getReturnType())
													.build();
		
		return information;
	}

}
