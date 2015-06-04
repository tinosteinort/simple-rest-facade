package de.tse.simplerestfacade.invocation;

import java.util.List;

public interface MethodInformation {

	String getMethodUrl();
	List<KeyValue> getQueryParameter();
	List<KeyValue> getHeaderParameter();
	String getMediaType();
	Object getPayload();
	Class<?> getPayloadType();
	Class<?> getReturnType();
	String getHttpMethod();
	
	default boolean hasPayload() {
	    return getPayload() != null;
	}
}
