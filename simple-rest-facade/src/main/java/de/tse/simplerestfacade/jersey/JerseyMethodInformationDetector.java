package de.tse.simplerestfacade.jersey;

import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.jersey.collector.MethodInformationCollector;

public class JerseyMethodInformationDetector implements RestInformationDetector {

//	private final UrlGenerator urlGenerator = new CachedUrlGenerator();
//	private final PayloadDetector payloadDetector = new CachedPayloadDetector();
//	private final QueryParameterDetector queryParamDetector = new CachedQueryParameterDetector();
//	private final HeaderParameterDetector headerParameterDetector = new CachedHeaderParameterDetector();
	
//	private final MethodCache methodCache = new DefaultMethodCache();
//	private final MethodInformationBuilder methodInformationBuilder = new MethodInformationBuilder();
	
	private final MethodInformationCollector informationCollector = new MethodInformationCollector();
	
	@Override
	public MethodInformation detectRestInformations(final MethodCall methodCall, final String mediaType) {
		
//		methodCache.buildCache(methodCall);
//		final CachableMethodData cachedMethodData = methodCache.getCachedData(methodCall);
//		final MethodInformation information = methodInformationBuilder.build(cachedMethodData, methodCall, mediaType);
		
		
		final MethodInformation information = informationCollector.collect(methodCall, mediaType);
		// TODO use 'interface RestInformationDetector<T extends MethodInformation>' ?
		
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
