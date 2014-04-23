package de.tse.simplerestfacade.jersey;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.cache.CachableMethodData;

public interface MethodCache {
	
	void buildCache(MethodCall methodCall);
	CachableMethodData getCachedData(MethodCall method);
}