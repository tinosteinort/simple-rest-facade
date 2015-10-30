package de.tse.simplerestfacade.methodinformation.cache;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import de.tse.simplerestfacade.methodinformation.MethodCall;

public class DataCache {
	
	private final Map<Method, Map<String, CacheEntry<?>>> cache = new HashMap<>();
	private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
	
	public <T> T getOrCreateCacheValue(final MethodCall methodCall, final String key, final CacheCallback<T> callback) {
		return getCachedValue(methodCall, key, callback);
	}

    private <T> T getCachedValue(final MethodCall methodCall, final String key, final CacheCallback<T> callback) {
        rwLock.readLock().lock();
		try {
			if (valueIsCached(methodCall, key)) {
				return getValueFromCache(methodCall, key);
			}
		}
		finally {
			rwLock.readLock().unlock();
		}
		
		return createCachedValue(methodCall, key, callback);
    }

    private <T> T createCachedValue(final MethodCall methodCall, final String key, final CacheCallback<T> callback) {
        rwLock.writeLock().lock();
		try {
			if (valueIsCached(methodCall, key)) {
				return getValueFromCache(methodCall, key);
			}
			
			final T valueToCache = callback.detectValue();
			putValueIntoCache(methodCall, key, valueToCache);
			return valueToCache;
		}
		finally {
			rwLock.writeLock().unlock();
		}
    }
	
	private boolean valueIsCached(final MethodCall methodCall, final String key) {
		final Map<String, CacheEntry<?>> methodCache = cache.get(methodCall.getMethod());
		return methodCache == null ? false : methodCache.get(key) != null;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getValueFromCache(final MethodCall methodCall, final String key) {
		final Map<String, CacheEntry<?>> methodCache = cache.get(methodCall.getMethod());
		return (T) methodCache.get(key).getValue();
	}
	
	private <T> void putValueIntoCache(final MethodCall methodCall, final String key, final T value) {
		Map<String, CacheEntry<?>> methodCache = cache.get(methodCall.getMethod());
		if (methodCache == null) {
			methodCache = new HashMap<>();
			cache.put(methodCall.getMethod(), methodCache);
		}
		methodCache.put(key, new CacheEntry<T>(value));
	}
}