package de.tse.simplerestfacade;

import java.lang.reflect.Method;

import de.tse.simplerestfacade.invocation.MethodInformation;

public interface RestInformationDetector {

	MethodInformation detectRestInformations(Method method, Object[] args, String mediaType);
}
