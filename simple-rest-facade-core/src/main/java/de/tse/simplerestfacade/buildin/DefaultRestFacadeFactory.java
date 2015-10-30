package de.tse.simplerestfacade.buildin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.http.client.HttpClient;

import de.tse.simplerestfacade.ExceptionHandler;
import de.tse.simplerestfacade.RestFacadeFactory;
import de.tse.simplerestfacade.RestInterfaceValidator;
import de.tse.simplerestfacade.invocation.RestInvocationHandler;
import de.tse.simplerestfacade.marshalling.MarshallingConfig;
import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;
import de.tse.simplerestfacade.methodexecution.ExecutionContext;

/**
 * Entry Point for Creating a Client for a REST Service.<br/>
 * <br/>
 * <b>MediaType</b><br/>
 * While Creating a Facade for a REST Service, the Developer has to define which MediaType
 *  should be used for marshalling / unmarshalling the Data to / from the REST Service.
 *  All Methods in an Interface are written with the same MediaType.<br/>
 * <br/>
 * It is possible to provide Custom Marshaller: {@link de.tse.simplerestfacade.marshalling.MarshallingConfig}
 * <br/><br/>
 * <b>ExceptionHandler</b><br/>
 * For customizing Exception Handling of the Rest Facade, there is the {@link de.tse.simplerestfacade.ExceptionHandler}
 *  Interface.
 * <br/><br/>
 * <b>Example</b><br/>
 * This is the simplest Way to create an Instance of this Class:
 * <pre>
 * HttpClient httpClient = HttpClientBuilder.create().build()
 * URI uri = URI.create("http://localhost:8080/application");
 * RestFacadeFactory factory = new DefaultRestFacadeFactory(uri, httpClient, MediaType.APPLICATION_JSON);
 * </pre>
 * This Exampel creates a Facade with the default ExceptionHandler and with no additional
 *  Marshalling Configs. Because of the missing Marshalling Configs one 
 *      <li>simpple-rest-facade-json</li>
 *      <li>simpple-rest-facade-xml</li>
 *  in the Classpath is required.
 * @author Tino
 */
public class DefaultRestFacadeFactory implements RestFacadeFactory {
	
    public final static boolean DEFAULT_VALIDATE_REST = true;
    
	private final URI endpoint;
	private final HttpClient httpClient;
	private final String defaultMediaType;
	private final Optional<RestInterfaceValidator> validator;
	private final ExceptionHandler defaultExceptionHandler;
    private final MarshallingConfigProvider marshallingConfigProvider;
	
    /**
     * Creates a full configured DefaultRestFacadeFactory.
     * 
     * @param endpoint The URI of the REST Service
     * @param httpClient The configured HttpClient to use for Communication with the REST Service
     * @param defaultMediaType The MediaType to use, if no other MediaType is defined on Creation of the Facade
     * @param validateRest if {@code true} the Facade Interface is validated before Creation, otherwise not
     * @param exceptionHandler The ExceptionHandler to use in Case of Error
     * @param additionalMarshallingConfigs List of additional MarshallingConfig. The Order of the Configs 1matters.
     */
    public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final String defaultMediaType,
            final boolean validateRest, final ExceptionHandler exceptionHandler,
            final List<MarshallingConfig> additionalMarshallingConfigs) {
        
	    this.endpoint = endpoint;
		this.httpClient = httpClient;
		this.defaultMediaType = defaultMediaType;
		this.validator = validateRest ? Optional.of(new DefaultRestInterfaceValidator()) : Optional.empty();
		this.defaultExceptionHandler = exceptionHandler;
		this.marshallingConfigProvider = new MarshallingConfigProvider(additionalMarshallingConfigs);
	}
	
    /**
     * Creates a new DefaultRestFacadeFactory with the default ExceptionHandler and no additional
     *  MarshallingConfigs.
     * 
     * @param endpoint The URI of the REST Service
     * @param httpClient The configured HttpClient to use for Communication with the REST Service
     * @param defaultMediaType The MediaType to use, if no other MediaType is defined on Creation of the Facade
     */
    public DefaultRestFacadeFactory(final URI endpoint, final HttpClient httpClient, final String defaultMediaType) {
        this(endpoint, httpClient, defaultMediaType, DEFAULT_VALIDATE_REST, new DefaultRestExceptionHandler(),
                Collections.emptyList());
    }

    @Override
    public <T> T createFacade(final Class<T> facadeClass) {
        return createFacade(facadeClass, defaultMediaType, defaultExceptionHandler);
    }
    
    @Override
    public <T> T createFacade(final Class<T> facadeClass, final String mediaType, final ExceptionHandler exceptionHandler) {

	    validator.ifPresent(validator -> validator.validate(facadeClass, mediaType));
		
        final ExecutionContext executionContext = new ExecutionContext(endpoint, mediaType, httpClient,
                marshallingConfigProvider, exceptionHandler);
		final InvocationHandler invocationHandler = new RestInvocationHandler(executionContext);
		
		return createProxy(facadeClass, invocationHandler);
	}
	
	@SuppressWarnings("unchecked")
    protected <T> T createProxy(final Class<T> facadeClass, final InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] { facadeClass },
                invocationHandler);
    }
}