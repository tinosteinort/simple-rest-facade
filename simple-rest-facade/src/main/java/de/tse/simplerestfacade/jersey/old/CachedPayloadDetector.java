package de.tse.simplerestfacade.jersey.old;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

class CachedPayloadDetector implements PayloadDetector {

	private final static int NO_PAYLOAD_INDEX = -1;
	
	private final Map<Method, Integer> parameterIndexCache = new HashMap<>();
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	
	@Override
	public Object detectPayload(final Method method, final Object[] args) {
		final int parameterIndex = determineIndexOfPayload(method);
		return parameterIndex == NO_PAYLOAD_INDEX ? null : args[parameterIndex];
	}

	private int determineIndexOfPayload(final Method method) {
		final Integer cachedIndex = getParameterIndexFromCache(method);
		if (cachedIndex != null) {
			return cachedIndex;
		}
		
		final int parameterIndex = findParameterIndex(method);
		putParameterIndexIntoCache(method, parameterIndex);
		
		return parameterIndex;
	}

	private int findParameterIndex(final Method method) {
		int parameterIndex = NO_PAYLOAD_INDEX;
		
		final Annotation[][] allParameterAnnotations = method.getParameterAnnotations();
		for (int i = 0; i < allParameterAnnotations.length; i++) {
			
			if (isPayloadParameter(allParameterAnnotations[i])) {
				parameterIndex = i;
				break;
			}
		}
		return parameterIndex;
	}
	
	private boolean isPayloadParameter(final Annotation[] annotationsOfParameter) {
		for (Annotation parameterAnnotation : annotationsOfParameter) {
			
			if (parameterAnnotation instanceof PathParam
					|| parameterAnnotation instanceof QueryParam) {
				return false;
			}
		}
		return true;
	}

	private Integer getParameterIndexFromCache(final Method method) {
		readWriteLock.readLock().lock();
		try {
			return parameterIndexCache.get(method);
		}
		finally {
			readWriteLock.readLock().unlock();
		}
	}
	
	private void putParameterIndexIntoCache(final Method method, final int index) {
		readWriteLock.writeLock().lock();
		try {
			parameterIndexCache.put(method, index);
		}
		finally {
			readWriteLock.writeLock().unlock();
		}
	}
}
