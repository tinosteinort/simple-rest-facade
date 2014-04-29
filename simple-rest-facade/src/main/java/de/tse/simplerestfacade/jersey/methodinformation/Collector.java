package de.tse.simplerestfacade.jersey.methodinformation;

import de.tse.simplerestfacade.invocation.MethodCall;

public interface Collector<T> {
	
	T collect(MethodCall methodCall);
	
	void apply(MethodCall methodCall, DefaultMethodInformation methodInformation, T data);
}