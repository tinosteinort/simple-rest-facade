package de.tse.simplerestfacade;

import de.tse.simplerestfacade.methodinformation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformation;

public interface RestInformationDetector {

	MethodInformation detectRestInformations(MethodCall methodCall);
}
