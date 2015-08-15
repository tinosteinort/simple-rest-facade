package de.tse.simplerestfacade;

/**
 * Interface of the Entry Point to create a Client for a REST Service.
 * 
 * @author Tino
 */
public interface RestFacadeFactory {

    /**
     * Create a Facade for the given Class, using the given Media Type and
     *  ExceptionHandler.
     * 
     * @param facadeClass The Interface with the REST Annotations
     * @param mediaType The Media Type to use
     * @param exceptionHandler The ExceptionHandler to use
     * @return A Proxy Object which maps a Call to a Method into a REST Call.
     * @see {@link javax.ws.rs.core.MediaType}
     * @see {@link de.tse.simplerestfacade.ExceptionHandler} 
     */
    <T> T createFacade(Class<T> facadeClass, String mediaType, ExceptionHandler exceptionHandler);
    
    /**
     * Create a Facade for the given Class, using the Media Type and ExceptionHandler
     *  which was defined by an Implementation of the RestFacadeFactory.
     *  
     * @param facadeClass The Interface with the REST Annotations
     * @return A Proxy Object which maps a Call to a Method into a REST Call.
     */
    <T> T createFacade(Class<T> facadeClass);
}
