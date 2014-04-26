package de.tse.simplerestfacade.jersey;

import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.jersey.collector.MethodInformationCollector;

public class JerseyMethodInformationDetector implements RestInformationDetector {

	private final MethodInformationCollector informationCollector = new MethodInformationCollector();
	
	@Override
	public MethodInformation detectRestInformations(final MethodCall methodCall, final String mediaType) {
		
		final MethodInformation information = informationCollector.collect(methodCall, mediaType);
		// TODO use 'interface RestInformationDetector<T extends MethodInformation>' ?
		
		return information;
	}
}
