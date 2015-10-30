package de.tse.simplerestfacade.methodinformation;

public interface Collector<T> {
	
	T collect(MethodCall methodCall);
	
	void apply(MethodCall methodCall, MethodInformationBuilder builder, T data);
}