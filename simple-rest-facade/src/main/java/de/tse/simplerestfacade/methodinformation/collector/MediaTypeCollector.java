package de.tse.simplerestfacade.methodinformation.collector;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformationBuilder;

public class MediaTypeCollector extends AbstractCollector<String> {

	@Override
	protected String collectData(final MethodCall methodCall) {
		return methodCall.getMediaType();
	}

	@Override
	public void apply(final MethodCall methodCall, final MethodInformationBuilder builder, final String mediaType) {
		builder.withMediaType(mediaType);
	}
}
