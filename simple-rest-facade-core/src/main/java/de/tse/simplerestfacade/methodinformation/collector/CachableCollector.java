package de.tse.simplerestfacade.methodinformation.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.methodinformation.Collector;
import de.tse.simplerestfacade.methodinformation.cache.CacheCallback;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;

public abstract class CachableCollector<T> implements Collector<T> {
	
	private final DataCache cache;
	
	public CachableCollector(final DataCache cache) {
		this.cache = cache;
	}
	
	@Override public T collect(final MethodCall methodCall) {
		return cache.getOrCreateCacheValue(methodCall, getCacheKey(), new CacheCallback<T>() {
			@Override public T detectValue() {
				return collectCachableData(methodCall);
			}
		});
	}
	
	protected abstract T collectCachableData(MethodCall methodCall);
	
	private String getCacheKey() {
		return getClass().getSimpleName();
	}
}