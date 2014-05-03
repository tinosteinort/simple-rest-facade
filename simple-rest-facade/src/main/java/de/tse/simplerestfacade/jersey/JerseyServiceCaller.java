package de.tse.simplerestfacade.jersey;

import java.net.URI;
import java.util.List;

import javax.ws.rs.HttpMethod;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;

import de.tse.simplerestfacade.RestClientException;
import de.tse.simplerestfacade.RestServiceCaller;
import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodInformation;

public class JerseyServiceCaller implements RestServiceCaller {

	private final Client client = Client.create();
	private final URI endpoint;
	
	public JerseyServiceCaller(final URI endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public Object callRestService(final MethodInformation methodInformation) throws RestClientException {
		
		WebResource webResource = client.resource(endpoint).path(methodInformation.getMethodUrl());
		webResource = applyQueryParams(webResource, methodInformation.getQueryParameter());
		
		Builder webResourceBuilder = webResource.accept(methodInformation.getMediaType()).entity(methodInformation.getPayload());
		webResourceBuilder = applyHeaderParams(webResourceBuilder, methodInformation.getHeaderParameter());
		
		try {
			return executeRequest(webResourceBuilder, methodInformation);
		}
		catch (UniformInterfaceException ex) {
			final ClientResponse response = ex.getResponse();
			throw new RestClientException(response.getStatus(), ex);
		}
		catch (ClientHandlerException ex) {
			throw new RestClientException(ex);
		}
	}
	
	private Object executeRequest(final Builder webResourceBuilder, final MethodInformation methodInformation) {
		
		final boolean hasPayload = methodInformation.getPayload() == null;
		
		switch (methodInformation.getHttpMethod()) {
		
			case HttpMethod.DELETE:
				if (hasPayload) {
					return webResourceBuilder.delete(methodInformation.getReturnType());
				}
				return webResourceBuilder.delete(methodInformation.getReturnType(), methodInformation.getPayload());
				
			case HttpMethod.GET:
				if (hasPayload) {
					return webResourceBuilder.get(methodInformation.getReturnType());
				}
				return webResourceBuilder.post(methodInformation.getReturnType(), methodInformation.getPayload());
				
			case HttpMethod.POST:
				if (hasPayload) {
					return webResourceBuilder.post(methodInformation.getReturnType());
				}
				return webResourceBuilder.post(methodInformation.getReturnType(), methodInformation.getPayload());
				
			case HttpMethod.PUT:
				if (hasPayload) {
					return webResourceBuilder.put(methodInformation.getReturnType());
				}
				return webResourceBuilder.put(methodInformation.getReturnType(), methodInformation.getPayload());
				
			case HttpMethod.HEAD:
			case HttpMethod.OPTIONS:
			default:
				throw new RestClientException("not supported http method: " + methodInformation.getHttpMethod());
		}
	}

	private WebResource applyQueryParams(final WebResource webResource, final List<KeyValue> queryParams) {
		
		WebResource newResource = webResource;
		for (KeyValue queryParam : queryParams) {
			final String stringValue = queryParam.getValue() == null ? "" : String.valueOf(queryParam.getValue());
			newResource = newResource.queryParam(queryParam.getKey(), stringValue);
		}
		
		return newResource;
	}

	private Builder applyHeaderParams(final Builder webResourceBuilder, final List<KeyValue> headerParams) {
		
		Builder newBuilder = webResourceBuilder;
		for (KeyValue headerParam : headerParams) {
			final String stringValue = headerParam.getValue() == null ? "" : String.valueOf(headerParam.getValue());
			newBuilder = webResourceBuilder.header(headerParam.getKey(), stringValue);
		}
		
		return newBuilder;
	}
}
