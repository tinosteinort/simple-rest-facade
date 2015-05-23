package de.tse.simplerestfacade;

public interface RestFacadeFactory {

    <T> T createFacade(Class<T> facadeClass, String mediaType);
    <T> T createFacade(Class<T> facadeClass);
}
