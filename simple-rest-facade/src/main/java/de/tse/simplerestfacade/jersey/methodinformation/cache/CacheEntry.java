package de.tse.simplerestfacade.jersey.methodinformation.cache;

class CacheEntry<T> {
	
	private final T value;
	
	public CacheEntry(final T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "[" + getClass().getSimpleName() + ": value=" + value + " ]";
	}
}