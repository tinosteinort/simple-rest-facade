package de.tse.simplerestfacade.jersey;

import java.lang.reflect.Method;
import java.util.List;

import de.tse.simplerestfacade.invocation.KeyValue;

public interface QueryParameterDetector {

	List<KeyValue> detectQueryParameter(Method method, Object[] args);
}
