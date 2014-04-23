package de.tse.simplerestfacade.jersey.cache;

interface ParameterAnnotationDataCollector {
	
	void collectAndApply(ParameterInfo parameterInfo, CachableMethodData methodData);
}