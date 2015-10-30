package de.tse.simplerestfacade.buildin;

import de.tse.simplerestfacade.RestClientException;
import de.tse.simplerestfacade.methodinformation.MethodInformation;


public interface RestServiceCaller {

	Object callRestService(MethodInformation methodInformation) throws RestClientException;
}
