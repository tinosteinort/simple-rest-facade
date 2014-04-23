package de.tse.simplerestfacade.jersey.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.tse.simplerestfacade.jersey.MethodCache;

public class DefaultMethodCache implements MethodCache {
	
	private final CachableDataCollector cachableDataCollector = new CachableDataCollector();
	
	private final Map<Method, CachableMethodData> cache = new HashMap<>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public DefaultMethodCache() {
		
	}
	
	public void buildCache(final Method method, final Object[] args) {
		readWriteLock.writeLock().lock();
		try {
			if (cache.containsKey(method)) {
				return;
			}
			
			final CachableMethodData info = cachableDataCollector.collectCachableData(method, args);
			cache.put(method, info);
		}
		finally {
			readWriteLock.writeLock().unlock();
		}
	}
	
	public CachableMethodData getCachedData(final Method method) {
		readWriteLock.readLock().lock();
		try {
			return cache.get(method);
		}
		finally {
			readWriteLock.readLock().unlock();
		}
	}
}