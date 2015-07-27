package de.tse.simplerestfacade;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.junit.Assert;
import org.junit.Test;

import de.tse.simplerestfacade.marshalling.Marshaller;
import de.tse.simplerestfacade.marshalling.MarshallingConfig;
import de.tse.simplerestfacade.marshalling.MarshallingConfigProvider;
import de.tse.simplerestfacade.marshalling.Unmarshaller;

public class MarshallingConfigProviderCoreTest {

    @Test(expected = RestClientException.class)
    public void testWithoutAnyMarshallingConfigProvider() {
        
        MarshallingConfigProvider provider = new MarshallingConfigProvider(Collections.emptyList());
        provider.getMarshaller(MediaType.APPLICATION_XML);
    }
    
    @Test
    public void testWithOneCustomMarshallingConfigProvider() {
        
        List<MarshallingConfig> configs = Arrays.asList(new DummyXmlMarshallingConfig());
        
        MarshallingConfigProvider provider = new MarshallingConfigProvider(configs);
        
        DummyMarshaller marshaller = (DummyMarshaller) provider.getMarshaller(MediaType.APPLICATION_XML);
        Assert.assertNotNull(marshaller);
        Assert.assertEquals(MediaType.APPLICATION_XML, marshaller.getMediaType());
        
        DummyUnmarshaller unmarshaller = (DummyUnmarshaller) provider.getUnmarshaller(MediaType.APPLICATION_XML);
        Assert.assertNotNull(unmarshaller);
        Assert.assertEquals(MediaType.APPLICATION_XML, unmarshaller.getMediaType());
    }
    
    @Test
    public void testWithMultipleCustomMarshallingConfigProvider() {
        
        List<MarshallingConfig> configs = Arrays.asList(new DummyXmlMarshallingConfig(),
                                                        new DummyJsonMarshallingConfig());
        
        MarshallingConfigProvider provider = new MarshallingConfigProvider(configs);
        
        DummyMarshaller xmlMarshaller = (DummyMarshaller) provider.getMarshaller(MediaType.APPLICATION_XML);
        Assert.assertNotNull(xmlMarshaller);
        Assert.assertEquals(MediaType.APPLICATION_XML, xmlMarshaller.getMediaType());
        
        DummyUnmarshaller xmlUnmarshaller = (DummyUnmarshaller) provider.getUnmarshaller(MediaType.APPLICATION_XML);
        Assert.assertNotNull(xmlUnmarshaller);
        Assert.assertEquals(MediaType.APPLICATION_XML, xmlUnmarshaller.getMediaType());

        DummyMarshaller jsonMarshaller = (DummyMarshaller) provider.getMarshaller(MediaType.APPLICATION_JSON);
        Assert.assertNotNull(jsonMarshaller);
        Assert.assertEquals(MediaType.APPLICATION_JSON, jsonMarshaller.getMediaType());
        
        DummyUnmarshaller jsonUnmarshaller = (DummyUnmarshaller) provider.getUnmarshaller(MediaType.APPLICATION_JSON);
        Assert.assertNotNull(jsonUnmarshaller);
        Assert.assertEquals(MediaType.APPLICATION_JSON, jsonUnmarshaller.getMediaType());
    }
    
    @Test(expected = RestClientException.class)
    public void testWithMultipleCustomMarshallingConfigProviderWithoutResult() {

        List<MarshallingConfig> configs = Arrays.asList(new DummyXmlMarshallingConfig(),
                                                        new DummyJsonMarshallingConfig());

        MarshallingConfigProvider provider = new MarshallingConfigProvider(configs);
        
        provider.getMarshaller(MediaType.APPLICATION_XHTML_XML);
    }
}

class DummyXmlMarshallingConfig implements MarshallingConfig {
    
    @Override public boolean supportsMediaType(final String mediaType) {
        return MediaType.APPLICATION_XML.equals(mediaType);
    }

    @Override public Unmarshaller getUnmarshaller() {
        return new DummyUnmarshaller(MediaType.APPLICATION_XML);
    }

    @Override public Marshaller getMarshaller() {
        return new DummyMarshaller(MediaType.APPLICATION_XML);
    }
}

class DummyJsonMarshallingConfig implements MarshallingConfig {
    
    @Override public boolean supportsMediaType(final String mediaType) {
        return MediaType.APPLICATION_JSON.equals(mediaType);
    }

    @Override public Unmarshaller getUnmarshaller() {
        return new DummyUnmarshaller(MediaType.APPLICATION_JSON);
    }

    @Override public Marshaller getMarshaller() {
        return new DummyMarshaller(MediaType.APPLICATION_JSON);
    }
}

class DummyMarshaller implements Marshaller {

    private String mediaType;
    
    public DummyMarshaller(final String mediaType) {
        this.mediaType = mediaType;
    }
    
    @Override public String marshall(final Object data) {
        return null;
    }
    
    public String getMediaType() {
        return mediaType;
    }
}

class DummyUnmarshaller implements Unmarshaller {

    private String mediaType;
    
    public DummyUnmarshaller(final String mediaType) {
        this.mediaType = mediaType;
    }
    
    @Override public Object unmarshall(final String string, final Class<?> targetClass) {
        return null;
    }
    
    public String getMediaType() {
        return mediaType;
    }
}
