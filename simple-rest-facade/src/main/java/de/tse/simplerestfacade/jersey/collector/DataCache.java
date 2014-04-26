package de.tse.simplerestfacade.jersey.collector;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.tse.simplerestfacade.invocation.MethodCall;

class DataCache {
	
	private final Map<Method, Map<String, CacheEntry<?>>> cache = new HashMap<>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	public <T> T createAndGetValue(final MethodCall methodCall, final String key, final CacheCallback<T> callback) {
		readWriteLock.readLock().lock();
		try {
			if (isValueCached(methodCall, key)) {
				return getCachedValue(methodCall, key);
			}
		}
		finally {
			readWriteLock.readLock().unlock();
		}
		
		readWriteLock.writeLock().lock();
		try {
			if (isValueCached(methodCall, key)) {
				return getCachedValue(methodCall, key);
			}
			
			final T valueToCache = callback.detectValue();
			putIntoCache(methodCall, key, valueToCache);
			return valueToCache;
		}
		finally {
			readWriteLock.writeLock().unlock();
		}
	}
	
	private boolean isValueCached(final MethodCall methodCall, final String key) {
		final Map<String, CacheEntry<?>> methodCache = cache.get(methodCall.getMethod());
		return methodCache == null ? false : methodCache.get(key) != null;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getCachedValue(final MethodCall methodCall, final String key) {
		final Map<String, CacheEntry<?>> methodCache = cache.get(methodCall.getMethod());
		return (T) methodCache.get(key).getValue();
	}
	
	private <T> void putIntoCache(final MethodCall methodCall, final String key, final T value) {
		Map<String, CacheEntry<?>> methodCache = cache.get(methodCall.getMethod());
		if (methodCache == null) {
			methodCache = new HashMap<>();
			cache.put(methodCall.getMethod(), methodCache);
		}
		methodCache.put(key, new CacheEntry<T>(value));
	}
	
	static interface CacheCallback<T> {
		T detectValue();
	}
}