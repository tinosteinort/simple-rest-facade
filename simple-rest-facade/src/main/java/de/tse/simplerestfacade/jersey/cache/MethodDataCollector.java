package de.tse.simplerestfacade.jersey.cache;

import java.lang.reflect.Method;

interface MethodDataCollector {
	
	void collectAndApply(Method method, CachableMethodData methodData);
}