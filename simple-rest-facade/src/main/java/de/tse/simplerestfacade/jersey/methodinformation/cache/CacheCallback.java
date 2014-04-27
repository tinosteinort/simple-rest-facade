package de.tse.simplerestfacade.jersey.methodinformation.cache;

public interface CacheCallback<T> {
	T detectValue();
}