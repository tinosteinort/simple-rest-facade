package de.tse.simplerestfacade;

public interface RestFacadeFactory {

    <T> T createFacade(Class<T> facadeClass, String mediaType, ExceptionHandler exceptionHandler);
    <T> T createFacade(Class<T> facadeClass);
}