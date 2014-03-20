package de.tse.simplerestfacade.jersey;

import de.tse.simplerestfacade.RestInterfaceValidator;

public class JerseyRestInterfaceValidator implements RestInterfaceValidator {

	@Override
	public void validate(final Class<?> interfaceClass, final String mediaType) {
		// TODO Ueberpruefen ob alle REST relevanten Methoden im Interface entweder
		// 		einen, oder keinen Parameter ohne @PathParam, @QueryParam haben.
		
		// Alle Methoden muessen mindestens einen gleichen mediaType haben damit sie
		//  unterstuetzt werden koennen
	}
}
