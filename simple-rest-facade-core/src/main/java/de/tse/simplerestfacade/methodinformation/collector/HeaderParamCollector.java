package de.tse.simplerestfacade.methodinformation.collector;

import java.util.List;

import javax.ws.rs.HeaderParam;

import de.tse.simplerestfacade.methodinformation.KeyValue;
import de.tse.simplerestfacade.methodinformation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformationBuilder;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;

public class HeaderParamCollector extends AnnotationCollector<HeaderParam> {

	public HeaderParamCollector(final DataCache cache) {
		super(cache, HeaderParam.class);
	}
	
	@Override protected String getValueFromAnnotation(final HeaderParam annotation) {
	    return annotation.value();
	}
	
    @Override public void apply(final MethodCall methodCall, final MethodInformationBuilder builder,
            final List<ParameterCacheInfo> headerParameterIndexes) {

        for (ParameterCacheInfo parameterInfo : headerParameterIndexes) {

            final Object value = methodCall.getArgs()[parameterInfo.getIndex()];
            final String key = parameterInfo.getParameterKey();
            builder.withHeaderParameter(new KeyValue(key, value));
        }
    }
}