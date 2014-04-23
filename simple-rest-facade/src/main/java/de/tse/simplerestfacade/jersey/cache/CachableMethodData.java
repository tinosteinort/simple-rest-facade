package de.tse.simplerestfacade.jersey.cache;

import java.util.ArrayList;
import java.util.List;

public class CachableMethodData {

	private final static int NO_PAYLOAD_INDEX = -1;
	
	private String urlTemplate;
	private int payloadIndex = NO_PAYLOAD_INDEX;
	private final List<ParameterCacheInfo> queryParameters = new ArrayList<>();
	private final List<ParameterCacheInfo> headerParameters = new ArrayList<>();
	
	public String getUrlTemplate() {
		return urlTemplate;
	}
	public void setUrlTemplate(String urlTemplate) {
		this.urlTemplate = urlTemplate;
	}
	
	public int getPayloadIndex() {
		return payloadIndex;
	}
	public void setPayloadIndex(int payloadIndex) {
		this.payloadIndex = payloadIndex;
	}
	public boolean hasPayload() {
		return payloadIndex != NO_PAYLOAD_INDEX;
	}
	
	public List<ParameterCacheInfo> getQueryParameters() {
		return queryParameters;
	}
	
	public List<ParameterCacheInfo> getHeaderParameters() {
		return headerParameters;
	}
}