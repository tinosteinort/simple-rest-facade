package de.tse.simplerestfacade.methodinformation.collector;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.MatrixParam;

import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformationBuilder;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;

public class MatrixParamCollector extends AbstractCollector<List<ParameterCacheInfo>> {

	public MatrixParamCollector(final DataCache cache) {
		super(cache);
	}
	
	@Override
	protected List<ParameterCacheInfo> collectData(final MethodCall methodCall) {
		
		final List<ParameterCacheInfo> matrixParameterIndexes = new ArrayList<>();
		
		final Annotation[][] allParameterAnnotations = methodCall.getMethod().getParameterAnnotations();
		for (int parameterIndex = 0; parameterIndex < allParameterAnnotations.length; parameterIndex++) {
			
			for (Annotation parameterAnnotation : allParameterAnnotations[parameterIndex]) {
				
				if (parameterAnnotation instanceof MatrixParam) {
					final String paramKey = ((MatrixParam) parameterAnnotation).value();
					matrixParameterIndexes.add(new ParameterCacheInfo(parameterIndex, paramKey));
				}
			}
		}
		
		return matrixParameterIndexes;
	}
	
	@Override
	public void apply(final MethodCall methodCall, final MethodInformationBuilder builder, final List<ParameterCacheInfo> matrixParameterIndexes) {
		
		for (ParameterCacheInfo parameterInfo : matrixParameterIndexes) {
			
			final Object value = methodCall.getArgs()[parameterInfo.getIndex()];
			final String key = parameterInfo.getParameterKey();
			builder.withMatrixParameter(new KeyValue(key, value));
		}
	}
}