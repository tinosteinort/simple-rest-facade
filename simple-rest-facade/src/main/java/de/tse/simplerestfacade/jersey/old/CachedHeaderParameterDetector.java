package de.tse.simplerestfacade.jersey.old;

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
import de.tse.simplerestfacade.jersey.cache.ParameterCacheInfo;

class CachedHeaderParameterDetector implements HeaderParameterDetector {

	private final Map<Method, List<ParameterCacheInfo>> headerParameterCache = new HashMap<>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	@Override
	public List<KeyValue> detectHeaderParameter(final Method method, final Object[] args) {
		if (args == null) {
			return Collections.emptyList();
		}
		
		List<ParameterCacheInfo> cacheInfo = getCachedParameterInfos(method);
		if (cacheInfo == null) {
			cacheInfo = doCacheParameter(method, args);
		}
		return getHeaderParameterFromCache(cacheInfo, args);
	}
	
	private List<KeyValue> getHeaderParameterFromCache(final List<ParameterCacheInfo> methodCacheInfo, final Object[] args) {
		final List<KeyValue> queryParams = new ArrayList<>();
		for (ParameterCacheInfo parameterInfo : methodCacheInfo) {
			
			final String paramKey = parameterInfo.getParameterKey();
			final Object paramValue = args[parameterInfo.getIndex()];

			queryParams.add(new KeyValue(paramKey, paramValue));
		}
		return queryParams;
	}
	
	private List<ParameterCacheInfo> doCacheParameter(final Method method, final Object[] args) {
		final List<ParameterCacheInfo> cacheInfos = new ArrayList<>();
		
		final Annotation[][] allParameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < allParameterAnnotations.length; i++) {

			final Annotation[] parameterAnnotations = allParameterAnnotations[i];
			for (Annotation parameterAnnotation : parameterAnnotations) {

				if (parameterAnnotation instanceof HeaderParam) {
					final String paramKey = ((HeaderParam) parameterAnnotation).value();

					cacheInfos.add(new ParameterCacheInfo(i, paramKey));
				}
			}
		}
		
		putParameterInfosIntoCache(method, cacheInfos);
		return cacheInfos;
	}
	
	private List<ParameterCacheInfo> getCachedParameterInfos(final Method method) {
		readWriteLock.readLock().lock();
		try {
			final List<ParameterCacheInfo> cacheInfos = headerParameterCache.get(method);
			return cacheInfos == null ? null : Collections.unmodifiableList(cacheInfos);
		}
		finally {
			readWriteLock.readLock().unlock();
		}
	}
	
	private void putParameterInfosIntoCache(final Method method, final List<ParameterCacheInfo> parameterInfos) {
		readWriteLock.writeLock().lock();
		try {
			List<ParameterCacheInfo> cacheInfos = headerParameterCache.get(method);
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