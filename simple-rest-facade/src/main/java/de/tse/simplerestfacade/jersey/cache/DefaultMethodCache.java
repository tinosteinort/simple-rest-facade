package de.tse.simplerestfacade.jersey.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.MethodCache;

public class DefaultMethodCache implements MethodCache {
	
	private final CachableDataCollector cachableDataCollector = new CachableDataCollector();
	
	private final Map<Method, CachableMethodData> cache = new HashMap<>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public DefaultMethodCache() {
		
	}
	
	public void buildCache(final MethodCall methodCall) {
		readWriteLock.writeLock().lock();
		try {
			if (cache.containsKey(methodCall.getMethod())) {
				return;
			}
			
			final CachableMethodData info = cachableDataCollector.collectCachableData(methodCall);
			cache.put(methodCall.getMethod(), info);
		}
		finally {
			readWriteLock.writeLock().unlock();
		}
	}
	
	public CachableMethodData getCachedData(final MethodCall methodCall) {
		readWriteLock.readLock().lock();
		try {
			return cache.get(methodCall.getMethod());
		}
		finally {
			readWriteLock.readLock().unlock();
		}
	}
}