package de.tse.simplerestfacade.jersey;

import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.jersey.cache.CachableMethodData;
import de.tse.simplerestfacade.jersey.cache.DefaultMethodCache;
import de.tse.simplerestfacade.jersey.methodinformation.MethodInformationBuilder;

public class JerseyMethodInformationDetector implements RestInformationDetector {

//	private final UrlGenerator urlGenerator = new CachedUrlGenerator();
//	private final PayloadDetector payloadDetector = new CachedPayloadDetector();
//	private final QueryParameterDetector queryParamDetector = new CachedQueryParameterDetector();
//	private final HeaderParameterDetector headerParameterDetector = new CachedHeaderParameterDetector();
	
	private final MethodCache methodCache = new DefaultMethodCache();
	private final MethodInformationBuilder methodInformationBuilder = new MethodInformationBuilder();
	
	@Override
	public MethodInformation detectRestInformations(final MethodCall methodCall, final String mediaType) {
		
		// TODO 'MethodCacheInfo cacheInfo = methodCache.getCacheInfos(method);' instead of *Generator and *Detector
		// TODO @FromParam, @MatrixParam, @CookieParam
		
		methodCache.buildCache(methodCall);
		final CachableMethodData cachedMethodData = methodCache.getCachedData(methodCall);
		
		// TODO Klassen aufteilen: Packages
		//							- methodinformation
		//							- cache
		final MethodInformation information = methodInformationBuilder.build(cachedMethodData, methodCall, mediaType);
		
		
		// In 'MethodCall' Objekt verpacken: Method method, Object[] args
		
		
//		final String methodUrl = urlGenerator.generate(method, args);
//		final Object payload = payloadDetector.detectPayload(method, args);
//		final List<KeyValue> queryParams = queryParamDetector.detectQueryParameter(method, args);
//		final List<KeyValue> headerParams = headerParameterDetector.detectHeaderParameter(method, args);
//		
//		final MethodInformation information = new MethodInformation.Builder()
//													.mediaType(mediaType)
//													.methodUrl(methodUrl)
//													.payload(payload)
//													.queryParameters(queryParams)
//													.headerParameters(headerParams)
//													.returnType(method.getReturnType())
//													.build();
		
		return information;
	}
}
