package de.tse.simplerestfacade;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import javax.ws.rs.Path;
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
    
    private MethodCall methodCall(final Class<?> serviceClass, final String methodName, final String mediaType, final Class<?> ...paramTypes) {
        final Method method;
        try {
            method = serviceClass.getMethod(methodName, paramTypes);
        }
        catch (NoSuchMethodException | SecurityException ex) {
            throw new RuntimeException(ex);
        }
        return new MethodCall(method, paramTypes, mediaType);
    }
}
