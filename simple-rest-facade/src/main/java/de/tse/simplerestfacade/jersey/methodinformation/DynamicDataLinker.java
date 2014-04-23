package de.tse.simplerestfacade.jersey.methodinformation;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.jersey.cache.CachableMethodData;

public interface DynamicDataLinker<T extends MethodInformation> {
	
	void apply(T methodInformation, CachableMethodData methodData, MethodCall methodCall, String mediaType);
}