package de.tse.simplerestfacade;

import java.util.Collections;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;

public class MarshallingConfigProviderBuildInTest {

    @Test
    public void testWithBuildInConfigProviders() {
        
        MarshallingConfigProvider provider = new MarshallingConfigProvider(Collections.emptyList());
        
        Assert.assertNotNull(provider.getMarshaller(MediaType.APPLICATION_XML));
        Assert.assertNotNull(provider.getUnmarshaller(MediaType.APPLICATION_JSON));
    }
    
    @Test(expected = RestClientException.class)
    public void testWithBuildInConfigProvidersNoResult() {

        MarshallingConfigProvider provider = new MarshallingConfigProvider(Collections.emptyList());
        
        Assert.assertNotNull(provider.getMarshaller(MediaType.APPLICATION_XML));
        Assert.assertNotNull(provider.getUnmarshaller(MediaType.APPLICATION_JSON));
        
        provider.getUnmarshaller(MediaType.APPLICATION_XHTML_XML);
    }
}
