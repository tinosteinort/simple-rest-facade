package de.tse.simplerestfacade.jersey;

import java.lang.reflect.Method;

import de.tse.simplerestfacade.jersey.cache.CachableMethodData;

public interface MethodCache {
	
	void buildCache(Method method, Object[] args);
	CachableMethodData getCachedData(Method method);
}