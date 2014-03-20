package de.tse.simplerestfacade.jersey;

import java.lang.reflect.Method;

public interface PayloadDetector {

	Object detectPayload(Method method, Object[] args);
}
