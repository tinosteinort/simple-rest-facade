package de.tse.simplerestfacade.methodinformation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;
import de.tse.simplerestfacade.methodinformation.collector.HeaderParamCollector;
import de.tse.simplerestfacade.methodinformation.collector.HttpMethodCollector;
import de.tse.simplerestfacade.methodinformation.collector.MatrixParamCollector;
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
											new HttpMethodCollector(cache),
											new MatrixParamCollector(cache)));
		// TODO @FromParam, @CookieParam
	}
	
	@Override
	public MethodInformation detectRestInformations(final MethodCall methodCall) {
		
	    final MethodInformationBuilder builder = new MethodInformationBuilder();
	    
		for (Collector<?> collector : dataCollectors) {
			collectAndApply(collector, methodCall, builder);
		}
		
		return builder.build();
	}
	
	private <T> void collectAndApply(final Collector<T> collector, final MethodCall methodCall, final MethodInformationBuilder builder) {
		final T data = collector.collect(methodCall);
		collector.apply(methodCall, builder, data);
	}
}
