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
		collectors.addAll(Arrays.asList(new MediaTypeCollector(),
										new QueryParamCollector(cache),
										new HeaderParamCollector(cache),
										new UrlTemplateCollector(cache),
										new PayloadCollector(cache),
										new ReturnTypeCollector(cache)));
		// TODO @FromParam, @MatrixParam, @CookieParam
	}
	
	public MethodInformation collect(final MethodCall methodCall) {
		
		final DefaultMethodInformation methodInformation = new DefaultMethodInformation();
		
		for (Collector<?> collector : collectors) {
			collectAndApply(collector, methodCall, methodInformation);
		}
		
		return methodInformation;
	}
	
	private <T> void collectAndApply(final Collector<T> collector, final MethodCall methodCall, final DefaultMethodInformation methodInformation) {
		final T data = collector.collect(methodCall);
		collector.apply(methodCall, methodInformation, data);
	}
}