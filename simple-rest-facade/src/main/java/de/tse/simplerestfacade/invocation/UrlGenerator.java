package de.tse.simplerestfacade.invocation;

import java.lang.reflect.Method;

public interface UrlGenerator {

	String generate(Method method, Object[] args);
}
