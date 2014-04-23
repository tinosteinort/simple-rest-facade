package de.tse.simplerestfacade.jersey.old;

import java.lang.reflect.Method;

public interface UrlGenerator {

	String generate(Method method, Object[] args);
}
