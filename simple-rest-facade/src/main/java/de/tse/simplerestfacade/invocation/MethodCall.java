package de.tse.simplerestfacade.invocation;

import java.lang.reflect.Method;

public class MethodCall {

	private final Method method;
	private final Object[] args;
	
	public MethodCall(final Method method, final Object[] args) {
		this.method = method;
		this.args = args;
	}
	
	public Method getMethod() {
		return method;
	}
	public Object[] getArgs() {
		return args;
	}
}
