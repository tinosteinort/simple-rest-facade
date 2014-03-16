package de.tse.simplerestfacade.invocation;

import java.lang.reflect.Method;

public interface PayloadDetector {

	Object detectPayload(Method method, Object[] args);
}
