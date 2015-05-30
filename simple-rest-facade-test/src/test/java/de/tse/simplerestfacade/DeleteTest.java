package de.tse.simplerestfacade;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

public class DeleteTest extends AbstractIntegrationTest {

    public static class TestDeleteImpl implements TestDeleteInterface {

        @Override public void deletePerson(final String userId) {
            
        }
    }
    
    @Path("deletetest")
    @Consumes(MediaType.APPLICATION_XML)
    public static interface TestDeleteInterface {

        @DELETE
        @Path("/delete/{id}")
        @Produces(MediaType.APPLICATION_XML)
        void deletePerson(@PathParam("id") String userId);
    }
    
    @Override
    protected Class<?>[] availableServerSideServices() {
        return new Class[] { TestDeleteImpl.class };
    }
    
    @Test
    public void testDelete() {
        
        TestDeleteInterface service = asRestClient(TestDeleteInterface.class, MediaType.APPLICATION_XML);
        
        service.deletePerson("1");
        Assert.assertTrue(true);
    }
}
