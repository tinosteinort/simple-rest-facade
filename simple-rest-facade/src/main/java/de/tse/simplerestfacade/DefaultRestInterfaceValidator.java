package de.tse.simplerestfacade;

public class DefaultRestInterfaceValidator implements RestInterfaceValidator {

	@Override
	public void validate(final Class<?> interfaceClass) {
		// TODO Ueberpruefen ob alle REST relevanten Methoden im Interface entweder
		// 		einen, oder keinen Parameter ohne @PathParam, @QueryParam haben.
		
		// Alle Methoden muessen mindestens einen gleichen mediaType haben damit sie
		//  unterstuetzt werden koennen
	}
}
