package de.tse.simplerestfacade.invocation;

import java.lang.reflect.Method;

public class MethodCall {

	private final Method method;
	private final Object[] args;
	private final String mediaType;
	
	public MethodCall(final Method method, final Object[] args, final String mediaType) {
		this.method = method;
		this.args = args;
		this.mediaType = mediaType;
	}
	
	public Method getMethod() {
		return method;
	}
	public Object[] getArgs() {
		return args;
	}
	public String getMediaType() {
		return mediaType;
	}
}
