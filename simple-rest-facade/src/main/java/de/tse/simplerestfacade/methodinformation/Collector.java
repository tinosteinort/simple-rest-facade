package de.tse.simplerestfacade.methodinformation;

import de.tse.simplerestfacade.invocation.MethodCall;

public interface Collector<T> {
	
	T collect(MethodCall methodCall);
	
	void apply(MethodCall methodCall, MethodInformationBuilder builder, T data);
}