package de.tse.simplerestfacade;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URI;

public abstract class RestFacadeFactory {

	protected final URI endpoint;
	
	public RestFacadeFactory(final URI endpoint) {
		this.endpoint = endpoint;
	}
	
	public RestFacadeFactory(final String endpoint) {
		this.endpoint = URI.create(endpoint);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T createProxy(final Class<T> facadeClass, final InvocationHandler invocationHandler) {
		return (T) Proxy.newProxyInstance(facadeClass.getClassLoader(), new Class[] { facadeClass }, invocationHandler);
	}
	
	public abstract <T> T createFacade(final Class<T> clazz, final String mediaType);
}
