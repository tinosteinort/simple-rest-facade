package de.tse.simplerestfacade;

import javax.ws.rs.core.Application;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;

public abstract class AbstractIntegrationTest extends JerseyTest {
    
    @Override
    protected Application configure() {
        return new ResourceConfig(availableServerSideServices());
    }

    protected abstract Class<?>[] availableServerSideServices();
    
    protected <T> T asRestClient(final Class<T> serviceClass, final String mediaType) {
        final HttpClient httpClient = HttpClientBuilder.create().build();
        final RestFacadeFactory factory = new DefaultRestFacadeFactory(getBaseUri(), httpClient, mediaType);
        return factory.createFacade(serviceClass);
    }
}
