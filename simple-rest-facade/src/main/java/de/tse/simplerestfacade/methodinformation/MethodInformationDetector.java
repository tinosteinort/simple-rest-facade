package de.tse.simplerestfacade.methodinformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;
import de.tse.simplerestfacade.methodinformation.collector.HeaderParamCollector;
import de.tse.simplerestfacade.methodinformation.collector.HttpMethodCollector;
import de.tse.simplerestfacade.methodinformation.collector.MediaTypeCollector;
import de.tse.simplerestfacade.methodinformation.collector.PayloadCollector;
import de.tse.simplerestfacade.methodinformation.collector.QueryParamCollector;
import de.tse.simplerestfacade.methodinformation.collector.ReturnTypeCollector;
import de.tse.simplerestfacade.methodinformation.collector.UrlCollector;

public class MethodInformationDetector implements RestInformationDetector {

	private final List<Collector<?>> dataCollectors = new ArrayList<>();
	private final DataCache cache = new DataCache();
	
	public MethodInformationDetector() {
		dataCollectors.addAll(Arrays.asList(new QueryParamCollector(cache),
											new MediaTypeCollector(),
											new HeaderParamCollector(cache),
											new UrlCollector(cache),
											new PayloadCollector(cache),
											new ReturnTypeCollector(cache),
											new HttpMethodCollector(cache)));
		// TODO @FromParam, @MatrixParam, @CookieParam
	}
	
	@Override
	public MethodInformation detectRestInformations(final MethodCall methodCall) {
		
		final DefaultMethodInformation methodInformation = new DefaultMethodInformation();
		
		for (Collector<?> collector : dataCollectors) {
			collectAndApply(collector, methodCall, methodInformation);
		}
		
		return methodInformation;
	}
	
	private <T> void collectAndApply(final Collector<T> collector, final MethodCall methodCall, final DefaultMethodInformation methodInformation) {
		final T data = collector.collect(methodCall);
		collector.apply(methodCall, methodInformation, data);
	}
}
