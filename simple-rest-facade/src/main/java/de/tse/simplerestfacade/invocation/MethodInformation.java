package de.tse.simplerestfacade.invocation;

import java.util.List;

public interface MethodInformation {

	String getMethodUrl();
	List<KeyValue> getQueryParameter();
	List<KeyValue> getHeaderParameter();
	List<KeyValue> getMatrixParameter();
	String getMediaType();
	Object getPayload();
	Class<?> getReturnType();
	String getHttpMethod();
	
	default boolean hasPayload() {
	    return getPayload() != null;
	}
}
