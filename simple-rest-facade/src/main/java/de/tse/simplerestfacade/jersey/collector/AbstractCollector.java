package de.tse.simplerestfacade.jersey.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;
import de.tse.simplerestfacade.jersey.collector.DataCache.CacheCallback;

abstract class AbstractCollector<T> implements Collector<T> {
	
	private final DataCache cache;
	
	public AbstractCollector(final DataCache cache) {
		this.cache = cache;
	}
	
	@Override
	public T collect(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final String mediaType) {
		if (supportsCaching()) {
			return cache.createAndGetValue(methodCall, getCacheKey(), new CacheCallback<T>() {
				@Override
				public T detectValue() {
					return collect(methodCall, mediaType);
				}
			});
		}
		return collect(methodCall, mediaType);
	}
	
	protected abstract boolean supportsCaching();
	
	protected abstract T collect(MethodCall methodCall, String mediaType);
	
	private String getCacheKey() {
		return getClass().getSimpleName();
	}
}