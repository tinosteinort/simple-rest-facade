package de.tse.simplerestfacade.invocation;

public class QueryParameterCacheInfo {
	
	private final int queryParameterIndex;
	private final String queryParameterKey;
	
	public QueryParameterCacheInfo(final int queryParameterIndex, final String queryParameterKey) {
		this.queryParameterIndex = queryParameterIndex;
		this.queryParameterKey = queryParameterKey;
	}
	
	public int getQueryParameterIndex() {
		return queryParameterIndex;
	}
	
	public String getQueryParameterKey() {
		return queryParameterKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + queryParameterIndex;
		result = prime * result + ((queryParameterKey == null) ? 0 : queryParameterKey.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryParameterCacheInfo other = (QueryParameterCacheInfo) obj;
		if (queryParameterIndex != other.queryParameterIndex)
			return false;
		if (queryParameterKey == null) {
			if (other.queryParameterKey != null)
				return false;
		} else if (!queryParameterKey.equals(other.queryParameterKey))
			return false;
		return true;
	}
}