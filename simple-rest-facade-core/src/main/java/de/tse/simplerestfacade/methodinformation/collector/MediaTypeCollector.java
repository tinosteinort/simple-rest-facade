package de.tse.simplerestfacade.methodinformation.collector;

import de.tse.simplerestfacade.methodinformation.Collector;
import de.tse.simplerestfacade.methodinformation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformationBuilder;

public class MediaTypeCollector implements Collector<String> {

    @Override
    public String collect(final MethodCall methodCall) {
        return methodCall.getMediaType();
    }
    
	@Override
	public void apply(final MethodCall methodCall, final MethodInformationBuilder builder, final String mediaType) {
		builder.withMediaType(mediaType);
	}
}
