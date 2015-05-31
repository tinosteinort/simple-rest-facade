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

    public static class TestDeleteWithPathParamImpl implements TestDeleteWithPathParamInterface {

        @Override public void deletePersonWithPathParams(final String userId) {
            
        }
    }
    
    @Path("deletetest")
    @Consumes(MediaType.APPLICATION_XML)
    public static interface TestDeleteWithPathParamInterface {

        @DELETE
        @Path("/deletewithpathparam/{id}")
        @Produces(MediaType.APPLICATION_XML)
        void deletePersonWithPathParams(@PathParam("id") String userId);
    }

    public static class TestDeleteWithoutParamsImpl implements TestDeleteWithoutParamsInterface {

        @Override public void deletePersonWithoutParams() {
            
        }
    }
    
    @Path("deletetest")
    @Consumes(MediaType.APPLICATION_XML)
    public static interface TestDeleteWithoutParamsInterface {

        @DELETE
        @Path("/deletewithoutparams")
        @Produces(MediaType.APPLICATION_XML)
        void deletePersonWithoutParams();
    }
    
    @Override protected Class<?>[] availableServerSideServices() {
        return new Class[] { TestDeleteWithPathParamImpl.class, TestDeleteWithoutParamsImpl.class };
    }
    
    @Test public void testDeleteWithPathParams() {
        
        TestDeleteWithPathParamInterface service = asRestClient(TestDeleteWithPathParamInterface.class, MediaType.APPLICATION_XML);
        
        service.deletePersonWithPathParams("1");
        Assert.assertTrue(true);
    }
    
    @Test public void testDeleteWithoutParams() {
        
        TestDeleteWithoutParamsInterface service = asRestClient(TestDeleteWithoutParamsInterface.class, MediaType.APPLICATION_XML);
        
        service.deletePersonWithoutParams();
        Assert.assertTrue(true);
    }
}
