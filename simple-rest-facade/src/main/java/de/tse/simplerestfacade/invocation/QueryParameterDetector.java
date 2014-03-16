package de.tse.simplerestfacade.invocation;

import java.lang.reflect.Method;
import java.util.List;

public interface QueryParameterDetector {

	List<QueryParameter> detectQueryParameter(Method method, Object[] args);
}
