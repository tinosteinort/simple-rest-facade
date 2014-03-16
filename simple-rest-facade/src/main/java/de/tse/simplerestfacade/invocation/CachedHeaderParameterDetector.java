package de.tse.simplerestfacade.invocation;

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

public class CachedHeaderParameterDetector implements HeaderParameterDetector {

	private final Map<Method, List<QueryParameterCacheInfo>> headerParameterCache = new HashMap<>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	@Override
	public List<QueryParameter> detectHeaderParameter(final Method method, final Object[] args) {
		if (args == null) {
			return Collections.emptyList();
		}
		
		List<QueryParameterCacheInfo> cacheInfo = getCachedParameterInfos(method);
		if (cacheInfo == null) {
			cacheInfo = doCacheParameter(method, args);
		}
		return getHeaderParameterFromCache(cacheInfo, args);
	}
	
	private List<QueryParameter> getHeaderParameterFromCache(final List<QueryParameterCacheInfo> methodCacheInfo, final Object[] args) {
		final List<QueryParameter> queryParams = new ArrayList<>();
		for (QueryParameterCacheInfo parameterInfo : methodCacheInfo) {
			
			final String paramKey = parameterInfo.getQueryParameterKey();
			final Object paramValue = args[parameterInfo.getQueryParameterIndex()];

			queryParams.add(new QueryParameter(paramKey, paramValue));
		}
		return queryParams;
	}
	
	private List<QueryParameterCacheInfo> doCacheParameter(final Method method, final Object[] args) {
		final List<QueryParameterCacheInfo> cacheInfos = new ArrayList<>();
		
		final Annotation[][] allParameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < allParameterAnnotations.length; i++) {

			final Annotation[] parameterAnnotations = allParameterAnnotations[i];
			for (Annotation parameterAnnotation : parameterAnnotations) {

				if (parameterAnnotation instanceof HeaderParam) {
					final String paramKey = ((HeaderParam) parameterAnnotation).value();

					cacheInfos.add(new QueryParameterCacheInfo(i, paramKey));
				}
			}
		}
		
		putParameterInfosIntoCache(method, cacheInfos);
		return cacheInfos;
	}
	
	private List<QueryParameterCacheInfo> getCachedParameterInfos(final Method method) {
		readWriteLock.readLock().lock();
		try {
			final List<QueryParameterCacheInfo> cacheInfos = headerParameterCache.get(method);
			return cacheInfos == null ? null : Collections.unmodifiableList(cacheInfos);
		}
		finally {
			readWriteLock.readLock().unlock();
		}
	}
	
	private void putParameterInfosIntoCache(final Method method, final List<QueryParameterCacheInfo> parameterInfos) {
		readWriteLock.writeLock().lock();
		try {
			List<QueryParameterCacheInfo> cacheInfos = headerParameterCache.get(method);
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