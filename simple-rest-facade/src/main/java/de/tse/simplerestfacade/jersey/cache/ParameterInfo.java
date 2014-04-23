package de.tse.simplerestfacade.jersey.cache;

import java.lang.annotation.Annotation;

class ParameterInfo {
	
	private final int index;
	private final Annotation[] annotations;
	
	public ParameterInfo(final int index, final Annotation[] annotations) {
		this.index = index;
		this.annotations = annotations;
	}
	
	public int getIndex() {
		return index;
	}
	
	public Annotation[] getAnnotations() {
		return annotations;
	}
}