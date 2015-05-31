package de.tse.simplerestfacade;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import de.tse.simplerestfacade.data.Person;

public class PutTest extends AbstractIntegrationTest {

    public static class TestPutWithDataImpl implements TestPutWithDataInterface {

        @Override public Person updatePersonWithData(final Person person) {
            person.setId(person.getId() + 1);
            person.setFirstname("UPDATED_" + person.getFirstname());
            person.setLastname("UPDATED_" + person.getLastname());
            person.setAge(person.getAge() + 1);
            return person;
        }
    }
    
    @Path("updatetest")
    @Consumes(MediaType.APPLICATION_XML)
    public static interface TestPutWithDataInterface {

        @PUT
        @Path("/updatewithdata")
        @Produces(MediaType.APPLICATION_XML)
        Person updatePersonWithData(Person person);
    }

    public static class TestPutWithNullImpl implements TestPutWithNullInterface {

        @Override public Person updatePersonWithNull(final Person person) {
            return null;
        }
    }
    
    @Path("updatewithnull")
    @Consumes(MediaType.APPLICATION_XML)
    public static interface TestPutWithNullInterface {

        @PUT
        @Path("/update")
        @Produces(MediaType.APPLICATION_XML)
        Person updatePersonWithNull(Person person);
    }
    
    @Override protected Class<?>[] availableServerSideServices() {
        return new Class[] { TestPutWithDataImpl.class, TestPutWithNullImpl.class };
    }
    
    @Test public void testPut() {
        
        TestPutWithDataInterface service = asRestClient(TestPutWithDataInterface.class, MediaType.APPLICATION_XML);
        
        Person person = new Person();
        person.setId(1);
        person.setFirstname("First");
        person.setLastname("Last");
        person.setAge(100);
        
        Person updatedPerson = service.updatePersonWithData(person);
        Assert.assertNotNull(updatedPerson);
        
        Assert.assertEquals((Integer) 2, updatedPerson.getId());
        Assert.assertEquals("UPDATED_First", updatedPerson.getFirstname());
        Assert.assertEquals("UPDATED_Last", updatedPerson.getLastname());
        Assert.assertEquals((Integer) 101, updatedPerson.getAge());
    }

    @Test public void testPutNull() {
        
        TestPutWithNullInterface service = asRestClient(TestPutWithNullInterface.class, MediaType.APPLICATION_XML);
        
        Person updatedPerson = service.updatePersonWithNull(null);
        Assert.assertNull(updatedPerson);
    }
}
