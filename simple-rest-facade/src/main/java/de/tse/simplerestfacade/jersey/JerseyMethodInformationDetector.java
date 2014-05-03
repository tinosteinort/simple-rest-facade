package de.tse.simplerestfacade.jersey;

import de.tse.simplerestfacade.RestInformationDetector;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.jersey.methodinformation.MethodInformationCollector;

public class JerseyMethodInformationDetector implements RestInformationDetector {

	private final MethodInformationCollector informationCollector = new MethodInformationCollector();
	
	@Override
	public MethodInformation detectRestInformations(final MethodCall methodCall) {
		
		return informationCollector.collect(methodCall);
	}
}
