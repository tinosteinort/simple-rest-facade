package de.tse.simplerestfacade.jersey.old;

import java.lang.reflect.Method;

public interface PayloadDetector {

	Object detectPayload(Method method, Object[] args);
}
