package de.tse.simplerestfacade;

import java.net.URI;
import java.util.List;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import de.tse.simplerestfacade.invocation.QueryParameter;

public class JerseyServiceCaller implements RestServiceCaller {

	private final Client client = Client.create();
	private final URI endpoint;
	
	public JerseyServiceCaller(final URI endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public Object callRestService(final String methodUrl, final List<QueryParameter> queryParams, final List<QueryParameter> headerParams, final String mediaType, final Object payload, final Class<?> returnType) {
		
		WebResource webResource = client.resource(endpoint).path(methodUrl);
		webResource = applyQueryParams(webResource, queryParams);
		
		Builder webResourceBuilder = webResource.accept(mediaType).entity(payload);
		webResourceBuilder = applyHeaderParams(webResourceBuilder, headerParams);
		
		if (payload == null) {
			return webResourceBuilder.get(returnType);
		}
		
		return webResourceBuilder.post(returnType, payload);
	}

	private WebResource applyQueryParams(final WebResource webResource, final List<QueryParameter> queryParams) {
		
		WebResource newResource = webResource;
		for (QueryParameter queryParam : queryParams) {
			final String stringValue = String.valueOf(queryParam.getValue()); // TODO "" or "null" if paramValue is null?
			newResource = newResource.queryParam(queryParam.getKey(), stringValue);
		}
		
		return newResource;
	}

	private Builder applyHeaderParams(final Builder webResourceBuilder, final List<QueryParameter> headerParams) {
		
		Builder newBuilder = webResourceBuilder;
		for (QueryParameter headerParam : headerParams) {
			final String stringValue = String.valueOf(headerParam.getValue()); // TODO "" or "null" if paramValue is null?
			newBuilder = webResourceBuilder.header(headerParam.getKey(), stringValue);
		}
		
		return newBuilder;
	}
}
