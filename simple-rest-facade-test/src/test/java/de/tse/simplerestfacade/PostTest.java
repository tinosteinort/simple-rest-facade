package de.tse.simplerestfacade;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import de.tse.simplerestfacade.data.Person;

public class PostTest extends AbstractIntegrationTest {

    public static class TestPostWithDataImpl implements TestPostWithDataInterface {

        @Override public Person createPersonWithData(final Person newPerson) {
            newPerson.setId(1);
            newPerson.setFirstname("NEW_" + newPerson.getFirstname());
            newPerson.setLastname("NEW_" + newPerson.getLastname());
            newPerson.setAge(newPerson.getAge() - 1);
            return newPerson;
        }
    }
    
    @Path("createtest")
    @Consumes(MediaType.APPLICATION_JSON)
    public static interface TestPostWithDataInterface {

        @POST
        @Path("/createwithdata")
        @Produces(MediaType.APPLICATION_JSON)
        Person createPersonWithData(Person person);
    }
    
    public static class TestPostWithNullImpl implements TestPostWithNullInterface {

        @Override public Person createPersonWithNull(Person person) {
            return null;
        }
    }

    @Path("createwithnull")
    @Consumes(MediaType.APPLICATION_JSON)
    public static interface TestPostWithNullInterface {

        @POST
        @Path("/createwithnull")
        @Produces(MediaType.APPLICATION_JSON)
        Person createPersonWithNull(Person person);
    }
    
    @Override protected Class<?>[] availableServerSideServices() {
        return new Class[] { TestPostWithDataImpl.class, TestPostWithNullImpl.class };
    }
    
    @Test public void testPost() {
        
        TestPostWithDataInterface service = asRestClient(TestPostWithDataInterface.class, MediaType.APPLICATION_JSON);
        
        Person newPerson = new Person();
        newPerson.setId(-1);
        newPerson.setFirstname("First");
        newPerson.setLastname("Last");
        newPerson.setAge(100);
        
        Person createdPerson = service.createPersonWithData(newPerson);
        Assert.assertNotNull(createdPerson);
        
        Assert.assertEquals((Integer) 1, createdPerson.getId());
        Assert.assertEquals("NEW_First", createdPerson.getFirstname());
        Assert.assertEquals("NEW_Last", createdPerson.getLastname());
        Assert.assertEquals((Integer) 99, createdPerson.getAge());
    }
    
    @Test public void testPostNull() {

        TestPostWithNullInterface service = asRestClient(TestPostWithNullInterface.class, MediaType.APPLICATION_JSON);
        Person createdPerson = service.createPersonWithNull(null);
        Assert.assertNull(createdPerson);
    }
}
