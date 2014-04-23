package de.tse.simplerestfacade.invocation;

import java.util.ArrayList;
import java.util.List;

public interface MethodInformation {

	String getMethodUrl();
	List<KeyValue> getQueryParameter();
	List<KeyValue> getHeaderParameter();
	String getMediaType();
	Object getPayload();
	Class<?> getReturnType();
	
//	static class Builder {
//		
//		private String methodUrl;
//		private final List<KeyValue> queryParameter = new ArrayList<>();
//		private final List<KeyValue> headerParameter = new ArrayList<>();
//		private String mediaType;
//		private Object payload;
//		private Class<?> returnType;
//		
//		public Builder methodUrl(final String url) {
//			this.methodUrl = url;
//			return this;
//		}
//		public Builder queryParameters(final List<KeyValue> queryParameters) {
//			this.queryParameter.addAll(queryParameters);
//			return this;
//		}
//		public Builder queryParameter(final KeyValue queryParameter) {
//			this.queryParameter.add(queryParameter);
//			return this;
//		}
//		public Builder headerParameters(final List<KeyValue> headerParameters) {
//			this.headerParameter.addAll(headerParameters);
//			return this;
//		}
//		public Builder headerParameter(final KeyValue headerParameter) {
//			this.headerParameter.add(headerParameter);
//			return this;
//		}
//		public Builder mediaType(final String mediaType) {
//			this.mediaType = mediaType;
//			return this;
//		}
//		public Builder payload(final Object payload) {
//			this.payload = payload;
//			return this;
//		}
//		public Builder returnType(final Class<?> returnType) {
//			this.returnType = returnType;
//			return this;
//		}
//		
//		public MethodInformation build() {
//			return new MethodInformation() {
//
//				@Override
//				public String getMethodUrl() {
//					return methodUrl;
//				}
//
//				@Override
//				public List<KeyValue> getQueryParameter() {
//					return queryParameter;
//				}
//
//				@Override
//				public List<KeyValue> getHeaderParameter() {
//					return queryParameter;
//				}
//
//				@Override
//				public String getMediaType() {
//					return mediaType;
//				}
//
//				@Override
//				public Object getPayload() {
//					return payload;
//				}
//
//				@Override
//				public Class<?> getReturnType() {
//					return returnType;
//				}
//				
//			};
//		}
//	}
}