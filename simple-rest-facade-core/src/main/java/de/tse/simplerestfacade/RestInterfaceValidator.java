package de.tse.simplerestfacade;

/**
 * Validator for Interfaces if they are usable as REST Facade
 * 
 * @author Tino
 */
public interface RestInterfaceValidator {

	/**
	 * Validates the given Class, if they is usable as REST Facade.
	 * 
	 * @param interfaceClass The Interface to check
	 * @param mediaType The MediaType to use by the Facade
	 */
	void validate(Class<?> interfaceClass, String mediaType);
}
