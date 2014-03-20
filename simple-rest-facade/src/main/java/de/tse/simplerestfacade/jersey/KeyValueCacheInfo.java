package de.tse.simplerestfacade.jersey;

class KeyValueCacheInfo {
	
	private final int index;
	private final String key;
	
	public KeyValueCacheInfo(final int index, final String key) {
		this.index = index;
		this.key = key;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getKey() {
		return key;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
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
		KeyValueCacheInfo other = (KeyValueCacheInfo) obj;
		if (index != other.index)
			return false;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
}