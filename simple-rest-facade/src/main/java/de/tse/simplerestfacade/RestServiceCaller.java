package de.tse.simplerestfacade;

import de.tse.simplerestfacade.invocation.MethodInformation;


public interface RestServiceCaller {

	Object callRestService(MethodInformation methodInformation);
}
