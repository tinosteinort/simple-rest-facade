package de.tse.simplerestfacade.jersey.methodinformation.collector;

public class ParameterCacheInfo {
	
	private final int index;
	private final String parameterKey;
	
	public ParameterCacheInfo(final int index, final String parameterKey) {
		this.index = index;
		this.parameterKey = parameterKey;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getParameterKey() {
		return parameterKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((parameterKey == null) ? 0 : parameterKey.hashCode());
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
		ParameterCacheInfo other = (ParameterCacheInfo) obj;
		if (index != other.index)
			return false;
		if (parameterKey == null) {
			if (other.parameterKey != null)
				return false;
		} else if (!parameterKey.equals(other.parameterKey))
			return false;
		return true;
	}
}