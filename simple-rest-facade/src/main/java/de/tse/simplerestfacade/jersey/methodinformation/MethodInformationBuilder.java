package de.tse.simplerestfacade.jersey.methodinformation;

import java.util.ArrayList;
import java.util.List;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.jersey.DefaultMethodInformation;
import de.tse.simplerestfacade.jersey.cache.CachableMethodData;

public class MethodInformationBuilder {
	
	private final List<DynamicDataLinker<DefaultMethodInformation>> dataLinkers = new ArrayList<>();
	
	public MethodInformationBuilder() {
		dataLinkers.add(new HeaderParameterLinker());
		dataLinkers.add(new QueryParameterLinker());
		dataLinkers.add(new MethodDataLinker());
	}
	
	public MethodInformation build(final CachableMethodData methodData, final MethodCall methodCall, final String mediaType) {
		
		final DefaultMethodInformation methodInformation = new DefaultMethodInformation();
		methodInformation.setMediaType(mediaType);
		
		for (DynamicDataLinker<DefaultMethodInformation> dataLinker : dataLinkers) {
			dataLinker.apply(methodInformation, methodData, methodCall, mediaType);
		}
		
		return methodInformation;
	}
}