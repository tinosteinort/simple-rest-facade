package de.tse.simplerestfacade.methodinformation;

import java.util.List;

public interface MethodInformation {

    String getMediaType();
    String getMethodUrl();
    String getHttpMethod();
    Object getPayload();
    Class<?> getReturnType();
	List<KeyValue> getQueryParameter();
	List<KeyValue> getHeaderParameter();
	List<KeyValue> getMatrixParameter();
	
	default boolean hasPayload() {
	    return getPayload() != null;
	}
}
