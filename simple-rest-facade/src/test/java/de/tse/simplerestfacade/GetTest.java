package de.tse.simplerestfacade;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import de.tse.simplerestfacade.data.Person;
import de.tse.simplerestfacade.marshalling.JaxbMarshaller;
import de.tse.simplerestfacade.marshalling.JaxbUnmarshaller;

public class GetTest extends JerseyTest {

    public static class TestGetImpl implements TestGetInterface {

        @Override public Person getPerson() {
            final Person person = new Person();
            person.setId(123);
            person.setFirstname("Max");
            person.setLastname("Mustermann");
            person.setAge(50);
            return person;
        }
    }
    
    @Path("testgetinterface")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static interface TestGetInterface {

        @GET
        @Path("/getperson")
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        Person getPerson();
    }
    
    @Override
    protected Application configure() {
        return new ResourceConfig(TestGetImpl.class);
    }
    
    private <T> T asService(final Class<T> serviceClass, final String mediaType) {
        final HttpClient httpClient = HttpClientBuilder.create().build();
        final RestFacadeFactory factory = new DefaultRestFacadeFactory(getBaseUri(), httpClient, new JaxbUnmarshaller(), new JaxbMarshaller());        
        return factory.createFacade(serviceClass, mediaType);
    }
    
    @Test
    public void test() {
        
        TestGetInterface service = asService(TestGetInterface.class, MediaType.APPLICATION_JSON);
        Person person = service.getPerson();
        Assert.assertNotNull(person);
        
        System.out.println(person.getId());
        System.out.println(person.getFirstname());
        System.out.println(person.getLastname());
        System.out.println(person.getAge());
    }
}
