package de.tse.simplerestfacade.methodinformation;

import java.util.ArrayList;
import java.util.List;

public class MethodInformationBuilder {

    private String mediaType;
    private String methodUrl;
    private String httpMethod;
    private Object payload;
    private Class<?> returnType;
    private final List<KeyValue> queryParameter = new ArrayList<>();
    private final List<KeyValue> headerParameter = new ArrayList<>();
    private final List<KeyValue> matrixParameter = new ArrayList<>();
    
    public MethodInformationBuilder withMediaType(final String mediaType) {
        this.mediaType = mediaType;
        return this;
    }
    public MethodInformationBuilder withMethodUrl(final String methodUrl) {
        this.methodUrl = methodUrl;
        return this;
    }
    public MethodInformationBuilder withHttpMethod(final String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }
    public MethodInformationBuilder withPayload(final Object payload) {
        this.payload = payload;
        return this;
    }
    public MethodInformationBuilder withReturnType(final Class<?> returnType) {
        this.returnType = returnType;
        return this;
    }
    public MethodInformationBuilder withQueryParameter(final KeyValue queryParam) {
        queryParameter.add(queryParam);
        return this;
    }
    public MethodInformationBuilder withHeaderParameter(final KeyValue headerParam) {
        headerParameter.add(headerParam);
        return this;
    }
    public MethodInformationBuilder withMatrixParameter(final KeyValue matrixParam) {
        matrixParameter.add(matrixParam);
        return this;
    }
    
    public MethodInformation build() {
        return new MethodInformation() {
            @Override public Class<?> getReturnType() {
                return returnType;
            }
            @Override public Object getPayload() {
                return payload;
            }
            @Override public String getMethodUrl() {
                return methodUrl;
            }
            @Override public String getMediaType() {
                return mediaType;
            }
            @Override public String getHttpMethod() {
                return httpMethod;
            }
            @Override public List<KeyValue> getQueryParameter() {
                return new ArrayList<>(queryParameter);
            }
            @Override public List<KeyValue> getHeaderParameter() {
                return new ArrayList<>(headerParameter);
            }
            @Override public List<KeyValue> getMatrixParameter() {
                return new ArrayList<>(matrixParameter);
            }
        };
    }
}
