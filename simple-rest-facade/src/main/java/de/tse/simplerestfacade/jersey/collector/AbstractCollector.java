package de.tse.simplerestfacade.jersey.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.collector.DataCache.CacheCallback;

abstract class AbstractCollector<T> implements Collector<T> {
	
	private final DataCache cache;
	
	public AbstractCollector() {
		this(null);
	}
	
	public AbstractCollector(final DataCache cache) {
		this.cache = cache;
	}
	
	@Override
	public T collect(final MethodCall methodCall) {
		if (supportsCaching()) {
			return cache.createAndGetValue(methodCall, getCacheKey(), new CacheCallback<T>() {
				@Override
				public T detectValue() {
					return collectData(methodCall);
				}
			});
		}
		return collectData(methodCall);
	}
	
	private boolean supportsCaching() {
		return cache != null;
	}
	
	protected abstract T collectData(MethodCall methodCall);
	
	private String getCacheKey() {
		return getClass().getSimpleName();
	}
}