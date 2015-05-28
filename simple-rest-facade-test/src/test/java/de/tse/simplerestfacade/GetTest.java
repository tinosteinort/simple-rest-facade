package de.tse.simplerestfacade;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import de.tse.simplerestfacade.data.Person;

public class GetTest extends AbstractIntegrationTest {

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
    protected Class<?>[] availableServerSideServices() {
        return new Class[] { TestGetImpl.class };
    }
    
    @Test
    public void testGet() {
        
        TestGetInterface service = asRestClient(TestGetInterface.class, MediaType.APPLICATION_JSON);
        Person person = service.getPerson();
        Assert.assertNotNull(person);
        
        Assert.assertEquals((Integer) 123, person.getId());
        Assert.assertEquals("Max", person.getFirstname());
        Assert.assertEquals("Mustermann", person.getLastname());
        Assert.assertEquals((Integer) 50, person.getAge());
    }
}
