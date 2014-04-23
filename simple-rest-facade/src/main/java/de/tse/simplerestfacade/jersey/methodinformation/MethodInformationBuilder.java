package de.tse.simplerestfacade.jersey.methodinformation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
	
	public MethodInformation build(final CachableMethodData methodData, final Method method, final Object[] args, final String mediaType) {
		
		final DefaultMethodInformation methodInformation = new DefaultMethodInformation();
		methodInformation.setMediaType(mediaType);
		
		for (DynamicDataLinker<DefaultMethodInformation> dataLinker : dataLinkers) {
			dataLinker.apply(methodInformation, methodData, method, args, mediaType);
		}
		
		return methodInformation;
	}
}