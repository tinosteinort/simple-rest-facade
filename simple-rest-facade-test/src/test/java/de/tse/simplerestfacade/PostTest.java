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

    public static class TestPostImpl implements TestPostInterface {

        @Override public Person createPerson(final Person newPerson) {
            newPerson.setId(1);
            newPerson.setFirstname("NEW_" + newPerson.getFirstname());
            newPerson.setLastname("NEW_" + newPerson.getLastname());
            newPerson.setAge(newPerson.getAge() - 1);
            return newPerson;
        }
    }
    
    @Path("createtest")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public static interface TestPostInterface {

        @POST
        @Path("/create")
        @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
        Person createPerson(Person person);
    }
    
    @Override
    protected Class<?>[] availableServerSideServices() {
        return new Class[] { TestPostImpl.class };
    }
    
    @Test
    public void testPost() {
        
        TestPostInterface service = asRestClient(TestPostInterface.class, MediaType.APPLICATION_JSON);
        
        Person newPerson = new Person();
        newPerson.setId(-1);
        newPerson.setFirstname("First");
        newPerson.setLastname("Last");
        newPerson.setAge(100);
        
        Person createdPerson = service.createPerson(newPerson);
        Assert.assertNotNull(createdPerson);
        
        Assert.assertEquals((Integer) 1, createdPerson.getId());
        Assert.assertEquals("NEW_First", createdPerson.getFirstname());
        Assert.assertEquals("NEW_Last", createdPerson.getLastname());
        Assert.assertEquals((Integer) 99, createdPerson.getAge());
    }
}
