package de.tse.simplerestfacade.jersey.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;

public class MediaTypeCollector extends AbstractCollector<String> {

	public MediaTypeCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected boolean supportsCaching() {
		return true;
	}
	
	@Override
	protected String collect(final MethodCall methodCall, final String mediaType) {
		return mediaType;
	}

	@Override
	public void apply(final MethodCall methodCall, final DefaultMethodInformation methodInformation, final String mediaType, final String data) {
		methodInformation.setMediaType(mediaType);
	}
}
