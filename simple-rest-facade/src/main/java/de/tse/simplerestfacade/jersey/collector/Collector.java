package de.tse.simplerestfacade.jersey.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;

interface Collector<T> {
	
	T collect(MethodCall methodCall);
	
	void apply(MethodCall methodCall, DefaultMethodInformation methodInformation, T data);
}