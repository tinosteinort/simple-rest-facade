package de.tse.simplerestfacade;

import java.util.List;

import de.tse.simplerestfacade.invocation.QueryParameter;


public interface RestServiceCaller {

	Object callRestService(String methodUrl, List<QueryParameter> queryParams, List<QueryParameter> headerParams, String mediaType, Object payload, Class<?> returnType);
}
