package de.tse.simplerestfacade.jersey;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.ws.rs.HeaderParam;

import de.tse.simplerestfacade.invocation.KeyValue;

class CachedHeaderParameterDetector implements HeaderParameterDetector {

	private final Map<Method, List<KeyValueCacheInfo>> headerParameterCache = new HashMap<>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	@Override
	public List<KeyValue> detectHeaderParameter(final Method method, final Object[] args) {
		if (args == null) {
			return Collections.emptyList();
		}
		
		List<KeyValueCacheInfo> cacheInfo = getCachedParameterInfos(method);
		if (cacheInfo == null) {
			cacheInfo = doCacheParameter(method, args);
		}
		return getHeaderParameterFromCache(cacheInfo, args);
	}
	
	private List<KeyValue> getHeaderParameterFromCache(final List<KeyValueCacheInfo> methodCacheInfo, final Object[] args) {
		final List<KeyValue> queryParams = new ArrayList<>();
		for (KeyValueCacheInfo parameterInfo : methodCacheInfo) {
			
			final String paramKey = parameterInfo.getKey();
			final Object paramValue = args[parameterInfo.getIndex()];

			queryParams.add(new KeyValue(paramKey, paramValue));
		}
		return queryParams;
	}
	
	private List<KeyValueCacheInfo> doCacheParameter(final Method method, final Object[] args) {
		final List<KeyValueCacheInfo> cacheInfos = new ArrayList<>();
		
		final Annotation[][] allParameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < allParameterAnnotations.length; i++) {

			final Annotation[] parameterAnnotations = allParameterAnnotations[i];
			for (Annotation parameterAnnotation : parameterAnnotations) {

				if (parameterAnnotation instanceof HeaderParam) {
					final String paramKey = ((HeaderParam) parameterAnnotation).value();

					cacheInfos.add(new KeyValueCacheInfo(i, paramKey));
				}
			}
		}
		
		putParameterInfosIntoCache(method, cacheInfos);
		return cacheInfos;
	}
	
	private List<KeyValueCacheInfo> getCachedParameterInfos(final Method method) {
		readWriteLock.readLock().lock();
		try {
			final List<KeyValueCacheInfo> cacheInfos = headerParameterCache.get(method);
			return cacheInfos == null ? null : Collections.unmodifiableList(cacheInfos);
		}
		finally {
			readWriteLock.readLock().unlock();
		}
	}
	
	private void putParameterInfosIntoCache(final Method method, final List<KeyValueCacheInfo> parameterInfos) {
		readWriteLock.writeLock().lock();
		try {
			List<KeyValueCacheInfo> cacheInfos = headerParameterCache.get(method);
			if (cacheInfos == null) {
				cacheInfos = new ArrayList<>();
			}
			cacheInfos.addAll(parameterInfos);
			headerParameterCache.put(method, cacheInfos);
		}
		finally {
			readWriteLock.writeLock().unlock();
		}
	}
}