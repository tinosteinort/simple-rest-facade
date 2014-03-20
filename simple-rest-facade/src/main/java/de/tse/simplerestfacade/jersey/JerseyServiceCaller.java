package de.tse.simplerestfacade.jersey;

import java.net.URI;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import de.tse.simplerestfacade.RestServiceCaller;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.invocation.KeyValue;

public class JerseyServiceCaller implements RestServiceCaller {

	private final Client client = Client.create();
	private final URI endpoint;
	
	public JerseyServiceCaller(final URI endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public Object callRestService(final MethodInformation methodInformation) {
		
		WebResource webResource = client.resource(endpoint).path(methodInformation.getMethodUrl());
		webResource = applyQueryParams(webResource, methodInformation.getQueryParameter());
		
		Builder webResourceBuilder = webResource.accept(methodInformation.getMediaType()).entity(methodInformation.getPayload());
		webResourceBuilder = applyHeaderParams(webResourceBuilder, methodInformation.getHeaderParameter());
		
		if (methodInformation.getPayload() == null) {
			return webResourceBuilder.get(methodInformation.getReturnType());
		}
		
		return webResourceBuilder.post(methodInformation.getReturnType(), methodInformation.getPayload());
	}

	private WebResource applyQueryParams(final WebResource webResource, final List<KeyValue> queryParams) {
		
		WebResource newResource = webResource;
		for (KeyValue queryParam : queryParams) {
			final String stringValue = String.valueOf(queryParam.getValue()); // TODO "" or "null" if paramValue is null?
			newResource = newResource.queryParam(queryParam.getKey(), stringValue);
		}
		
		return newResource;
	}

	private Builder applyHeaderParams(final Builder webResourceBuilder, final List<KeyValue> headerParams) {
		
		Builder newBuilder = webResourceBuilder;
		for (KeyValue headerParam : headerParams) {
			final String stringValue = String.valueOf(headerParam.getValue()); // TODO "" or "null" if paramValue is null?
			newBuilder = webResourceBuilder.header(headerParam.getKey(), stringValue);
		}
		
		return newBuilder;
	}
}
