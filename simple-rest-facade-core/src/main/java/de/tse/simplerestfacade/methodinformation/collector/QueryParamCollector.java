package de.tse.simplerestfacade.methodinformation.collector;

import java.util.List;

import javax.ws.rs.QueryParam;

import de.tse.simplerestfacade.methodinformation.KeyValue;
import de.tse.simplerestfacade.methodinformation.MethodCall;
import de.tse.simplerestfacade.methodinformation.MethodInformationBuilder;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;

public class QueryParamCollector extends AnnotationCollector<QueryParam> {

	public QueryParamCollector(final DataCache cache) {
		super(cache, QueryParam.class);
	}
	
	@Override protected String getValueFromAnnotation(final QueryParam annotation) {
	    return annotation.value();
	}
	
    @Override public void apply(final MethodCall methodCall, final MethodInformationBuilder builder,
            final List<ParameterCacheInfo> queryParameterIndexes) {

        for (ParameterCacheInfo parameterInfo : queryParameterIndexes) {

            final Object value = methodCall.getArgs()[parameterInfo.getIndex()];
            final String key = parameterInfo.getParameterKey();
            builder.withQueryParameter(new KeyValue(key, value));
        }
    }
}