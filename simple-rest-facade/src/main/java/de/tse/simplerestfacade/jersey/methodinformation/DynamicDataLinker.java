package de.tse.simplerestfacade.jersey.methodinformation;

import java.lang.reflect.Method;

import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.jersey.cache.CachableMethodData;

public interface DynamicDataLinker<T extends MethodInformation> {
	
	void apply(T methodInformation, CachableMethodData methodData, Method method, Object[] args, String mediaType);
}