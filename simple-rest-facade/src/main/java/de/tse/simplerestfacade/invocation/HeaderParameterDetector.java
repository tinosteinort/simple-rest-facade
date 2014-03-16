package de.tse.simplerestfacade.invocation;

import java.lang.reflect.Method;
import java.util.List;

public interface HeaderParameterDetector {

	List<QueryParameter> detectHeaderParameter(Method method, Object[] args);
}
