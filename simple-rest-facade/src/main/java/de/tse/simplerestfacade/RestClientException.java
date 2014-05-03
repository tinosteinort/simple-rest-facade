package de.tse.simplerestfacade;

public class RestClientException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private int httpResponseCode;
	
	public RestClientException() {
		
	}
	
	public RestClientException(final Throwable cause) {
		super(cause);
	}
	
	public RestClientException(final String message) {
		super(message);
	}
	
	public RestClientException(final String message, final Throwable cause) {
		super(message, cause);
	}
	
	public RestClientException(final int httpResponseCode) {
		this.httpResponseCode = httpResponseCode;
	}
	
	public RestClientException(final int httpResponseCode, final Throwable cause) {
		super(cause);
		this.httpResponseCode = httpResponseCode;
	}
	
	public int getHttpResponseCode() {
		return httpResponseCode;
	}
}
