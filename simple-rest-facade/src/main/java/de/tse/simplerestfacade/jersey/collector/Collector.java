package de.tse.simplerestfacade.jersey.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;

interface Collector<T> {
	
	T collect(MethodCall methodCall, DefaultMethodInformation methodInformation);
	
	void apply(final MethodCall methodCall, DefaultMethodInformation methodInformation, T data);
}