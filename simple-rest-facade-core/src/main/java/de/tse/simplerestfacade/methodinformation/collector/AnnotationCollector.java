package de.tse.simplerestfacade.methodinformation.collector;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.methodinformation.cache.DataCache;

public abstract class AnnotationCollector<T extends Annotation> extends CachableCollector<List<ParameterCacheInfo>> {

    private final Class<T> annotationType;
    
    public AnnotationCollector(final DataCache cache, final Class<T> annotation) {
        super(cache);
        this.annotationType = annotation;
    }
    
    @Override protected List<ParameterCacheInfo> collectCachableData(final MethodCall methodCall) {
        
        final List<ParameterCacheInfo> queryParameterIndexes = new ArrayList<>();
        
        final Annotation[][] allParameterAnnotations = methodCall.getMethod().getParameterAnnotations();
        for (int parameterIndex = 0; parameterIndex < allParameterAnnotations.length; parameterIndex++) {
            
            for (Annotation parameterAnnotation : allParameterAnnotations[parameterIndex]) {
                if (annotationType.isAssignableFrom(parameterAnnotation.getClass())) {
                    @SuppressWarnings("unchecked")
                    final String paramKey = getValueFromAnnotation((T) parameterAnnotation);
                    queryParameterIndexes.add(new ParameterCacheInfo(parameterIndex, paramKey));
                }
            }
        }
        
        return queryParameterIndexes;
    }

    protected abstract String getValueFromAnnotation(final T annotation);
}
