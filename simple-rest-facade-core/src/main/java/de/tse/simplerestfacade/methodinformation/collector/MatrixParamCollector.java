package de.tse.simplerestfacade.methodinformation.collector;

import java.util.List;

import javax.ws.rs.MatrixParam;

import de.tse.simplerestfacade.invocation.KeyValue;
import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformationBuilder;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;

public class MatrixParamCollector extends AnnotationCollector<MatrixParam> {

	public MatrixParamCollector(final DataCache cache) {
		super(cache, MatrixParam.class);
	}
	
	@Override protected String getValueFromAnnotation(final MatrixParam annotation) {
	    return annotation.value();
	}
	
    @Override public void apply(final MethodCall methodCall, final MethodInformationBuilder builder,
            final List<ParameterCacheInfo> matrixParameterIndexes) {

        for (ParameterCacheInfo parameterInfo : matrixParameterIndexes) {

            final Object value = methodCall.getArgs()[parameterInfo.getIndex()];
            final String key = parameterInfo.getParameterKey();
            builder.withMatrixParameter(new KeyValue(key, value));
        }
    }
}