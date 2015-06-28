package de.tse.simplerestfacade.methodinformation;

import java.util.ArrayList;
import java.util.List;

import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodInformation;

public class DefaultMethodInformation implements MethodInformation {

	private String methodUrl;
	private final List<KeyValue> queryParameter = new ArrayList<>();
	private final List<KeyValue> headerParameter = new ArrayList<>();
	private final List<KeyValue> matrixParameter = new ArrayList<>();
	private String mediaType;
	private Object payload;
	private Class<?> payloadType;
	private Class<?> returnType;
	private String httpMethod;
	
	@Override
	public String getMethodUrl() {
		return methodUrl;
	}
	public void setMethodUrl(final String methodUrl) {
		this.methodUrl = methodUrl;
	}

	@Override
	public List<KeyValue> getQueryParameter() {
		return queryParameter;
	}

	@Override
	public List<KeyValue> getHeaderParameter() {
		return headerParameter;
	}

    @Override
    public List<KeyValue> getMatrixParameter() {
        return matrixParameter;
    }

	@Override
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(final String mediaType) {
		this.mediaType = mediaType;
	}

	@Override
	public Object getPayload() {
		return payload;
	}
	public void setPayload(final Object payload) {
		this.payload = payload;
	}
	
	@Override
	public Class<?> getPayloadType() {
        return payloadType;
    }
	public void setPayloadType(Class<?> payloadType) {
        this.payloadType = payloadType;
    }

	@Override
	public Class<?> getReturnType() {
		return returnType;
	}
	public void setReturnType(final Class<?> returnType) {
		this.returnType = returnType;
	}
	
	@Override
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(final String httpMethod) {
		this.httpMethod = httpMethod;
	}
}
