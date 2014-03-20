package de.tse.simplerestfacade.jersey;

import java.lang.reflect.Method;

public interface UrlGenerator {

	String generate(Method method, Object[] args);
}
