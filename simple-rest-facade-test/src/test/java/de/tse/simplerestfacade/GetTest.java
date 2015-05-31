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

    public static class TestGetWithResultImpl implements TestGetWithResultInterface {

        @Override public Person getPersonWithResult() {
            final Person person = new Person();
            person.setId(123);
            person.setFirstname("Max");
            person.setLastname("Mustermann");
            person.setAge(50);
            return person;
        }
    }
    
    @Path("gettest")
    @Consumes(MediaType.APPLICATION_XML)
    public static interface TestGetWithResultInterface {

        @GET
        @Path("/getwithresult")
        @Produces(MediaType.APPLICATION_XML)
        Person getPersonWithResult();
    }

    public static class TestGetNullImpl implements TestGetNullInterface {

        @Override public Person getPersonNullResult() {
            return null;
        }
    }
    
    @Path("gettestnull")
    @Consumes(MediaType.APPLICATION_XML)
    public static interface TestGetNullInterface {

        @GET
        @Path("/getnull")
        @Produces(MediaType.APPLICATION_XML)
        Person getPersonNullResult();
    }
    
    @Override protected Class<?>[] availableServerSideServices() {
        return new Class[] { TestGetWithResultImpl.class, TestGetNullImpl.class };
    }
    
    @Test public void testGet() {
        
        TestGetWithResultInterface service = asRestClient(TestGetWithResultInterface.class, MediaType.APPLICATION_XML);
        Person person = service.getPersonWithResult();
        Assert.assertNotNull(person);
        
        Assert.assertEquals((Integer) 123, person.getId());
        Assert.assertEquals("Max", person.getFirstname());
        Assert.assertEquals("Mustermann", person.getLastname());
        Assert.assertEquals((Integer) 50, person.getAge());
    }
    
    @Test public void testGetNull() {

        TestGetNullInterface service = asRestClient(TestGetNullInterface.class, MediaType.APPLICATION_XML);
        Person person = service.getPersonNullResult();
        Assert.assertNull(person);
    }
}
