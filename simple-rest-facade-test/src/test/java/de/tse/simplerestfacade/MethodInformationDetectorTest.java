package de.tse.simplerestfacade;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

import de.tse.simplerestfacade.invocation.MethodCall;
import de.tse.simplerestfacade.invocation.MethodInformation;
import de.tse.simplerestfacade.methodinformation.MethodInformationDetector;

public class MethodInformationDetectorTest {

    public static interface TestDetectMediaType {
        
        void interfaceMethod();
    }
    
    @Test public void testDetectMediaType() {
        
        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectMediaType.class, "interfaceMethod", MediaType.APPLICATION_XML);
        MethodInformation info = detector.detectRestInformations(call);
        assertEquals(MediaType.APPLICATION_XML, info.getMediaType());
    }
    
    @Path("service")
    public static interface TestDetectMethodPath {
        
        @Path("/method")
        void interfaceMethod();
    }
    
    @Test public void testDetectMethoPath() {

        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectMethodPath.class, "interfaceMethod", MediaType.APPLICATION_XML);
        MethodInformation info = detector.detectRestInformations(call);
        
        assertEquals("service/method", info.getMethodUrl());
    }
    
    @Path("service")
    public static interface TestDetectMethodPathWithPathParams {
        
        @Path("/method/{id}/{attribute}")
        void interfaceMethod(@PathParam("id") String param1, @PathParam("attribute") String param2);
    }
    
    @Test public void testDetectMethodPathWithPathParams() {
        
        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectMethodPathWithPathParams.class, "interfaceMethod", MediaType.APPLICATION_XML, "1234", "attribute");
        MethodInformation info = detector.detectRestInformations(call);
        
        assertEquals("service/method/1234/attribute", info.getMethodUrl());        
    }

    private MethodCall methodCall(final Class<?> serviceClass, final String methodName, final String mediaType, final Object ...params) {
        final Class<?>[] paramTypes = Arrays.stream(params).map(param -> param.getClass()).toArray(value -> new Class<?>[value]);
        final Method method;
        try {
            method = serviceClass.getMethod(methodName, paramTypes);
        }
        catch (NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
        return new MethodCall(method, params, mediaType);
    }
}
