package de.tse.simplerestfacade.jersey.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;

public class MediaTypeCollector extends AbstractCollector<String> {

	@Override
	protected String collect(final MethodCall methodCall) {
		return methodCall.getMediaType();
	}

	@Override
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final String mediaType) {
		methodInformation.setMediaType(mediaType);
	}
}
