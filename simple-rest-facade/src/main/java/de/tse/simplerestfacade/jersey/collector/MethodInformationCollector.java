package de.tse.simplerestfacade.jersey.collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;

public class MethodInformationCollector {

	private final List<Collector<?>> collectors = new ArrayList<>();
	private final DataCache cache = new DataCache();
	
	public MethodInformationCollector() {
		collectors.addAll(Arrays.asList(new QueryParamCollector(cache),
										new HeaderParamCollector(cache),
										new UrlTemplateCollector(cache),
										new MediaTypeCollector(cache),
										new ReturnTypeCollector(cache),
										new PayloadCollector(cache)));
		// TODO @FromParam, @MatrixParam, @CookieParam
	}
	
	public MethodInformation collect(final MethodCall methodCall, final String mediaType) {
		
		final DefaultMethodInformation methodInformation = new DefaultMethodInformation();
		
		for (Collector<?> collector : collectors) {
			collectAndApply(collector, methodCall, methodInformation, mediaType);
		}
		
		return methodInformation;
	}
	
	private <T> void collectAndApply(final Collector<T> collector, final MethodCall methodCall, final DefaultMethodInformation methodInformation, final String mediaType) {
		final T data = collector.collect(methodCall, methodInformation, mediaType);
		collector.apply(methodCall, methodInformation, mediaType, data);
	}
}