package de.tse.simplerestfacade;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;

public interface RestInformationDetector {

	MethodInformation detectRestInformations(MethodCall methodCall);
}
