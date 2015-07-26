package de.tse.simplerestfacade;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
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
    public static interface TestDetectMethodUrl {
        
        @Path("/method")
        void interfaceMethod();
    }
    
    @Test public void testDetectMethodUrl() {

        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectMethodUrl.class, "interfaceMethod", MediaType.APPLICATION_XML);
        MethodInformation info = detector.detectRestInformations(call);
        
        assertEquals("service/method", info.getMethodUrl());
    }
    
    @Path("service")
    public static interface TestDetectMethodUrlWithPathParams {
        
        @Path("/method/{id}/{attribute}")
        void interfaceMethod(@PathParam("id") String param1, @PathParam("attribute") String param2);
    }
    
    @Test public void testDetectMethodUrlWithPathParams() {
        
        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectMethodUrlWithPathParams.class, "interfaceMethod", MediaType.APPLICATION_XML, "1234", "attribute");
        MethodInformation info = detector.detectRestInformations(call);
        
        assertEquals("service/method/1234/attribute", info.getMethodUrl());        
    }
    
    @Path("service")
    public static interface TestDetectPayload {
        
        @Path("/methodWithPayload")
        void interfaceMethod(@PathParam("pathParam") String pathParam,
                             @QueryParam("queryParam") String queryParam,
                             @MatrixParam("matrixParam") String matrixParam,
                             String payload);
    }
    
    @Test public void testDetectPayload() {
        
        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectPayload.class, "interfaceMethod", MediaType.APPLICATION_XML, "path", "query", "matrix", "payloadData");
        MethodInformation info = detector.detectRestInformations(call);
        
        assertEquals("payloadData", info.getPayload());
    }
    
    @Path("service")
    public static interface TestDetectHttpMethod {
        
        @Path("/method")
        @POST
        void interfaceMethod();
    }
    
    @Test public void testDetectHttpMethod() {
        
        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectHttpMethod.class, "interfaceMethod", MediaType.APPLICATION_XML);
        MethodInformation info = detector.detectRestInformations(call);
        
        assertEquals(HttpMethod.POST, info.getHttpMethod());
    }
    
    @Path("service")
    public static interface TestDetectQueryParameter {
        
        @Path("/method/{value}/{number}")
        void interfaceMethod(@QueryParam("value") String value, @QueryParam("number") Integer number);
    }
    
    @Test public void testDetectQueryParameter() {
        
        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectQueryParameter.class, "interfaceMethod", MediaType.APPLICATION_XML, "MyValue", 123);
        MethodInformation info = detector.detectRestInformations(call);
        
        assertEquals(2, info.getQueryParameter().size());
        assertEquals("value", info.getQueryParameter().get(0).getKey());
        assertEquals("MyValue", info.getQueryParameter().get(0).getValue());
        assertEquals("number", info.getQueryParameter().get(1).getKey());
        assertEquals(123, info.getQueryParameter().get(1).getValue());
    }

    @Path("service")
    public static interface TestDetectHeaderParameter {
        
        @Path("/method/{product}/{amount}")
        void interfaceMethod(@HeaderParam("product") String product, @HeaderParam("amount") Integer amount);
    }
    
    @Test public void testDetectHeaderParameter() {
        
        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectHeaderParameter.class, "interfaceMethod", MediaType.APPLICATION_XML, "Table", 2);
        MethodInformation info = detector.detectRestInformations(call);
        
        assertEquals(2, info.getHeaderParameter().size());
        assertEquals("product", info.getHeaderParameter().get(0).getKey());
        assertEquals("Table", info.getHeaderParameter().get(0).getValue());
        assertEquals("amount", info.getHeaderParameter().get(1).getKey());
        assertEquals(2, info.getHeaderParameter().get(1).getValue());
    }

    @Path("service")
    public static interface TestDetectMatrixParameter {
        
        @Path("/method/{one}/{two}/{three}")
        void interfaceMethod(@MatrixParam("one") String one, @MatrixParam("two") Integer two, @MatrixParam("three") String three);
    }
    
    @Test public void testDetectMatrixParameter() {
        
        MethodInformationDetector detector = new MethodInformationDetector();
        
        MethodCall call = methodCall(TestDetectMatrixParameter.class, "interfaceMethod", MediaType.APPLICATION_XML, "One", 2, "Three");
        MethodInformation info = detector.detectRestInformations(call);
        
        assertEquals(3, info.getMatrixParameter().size());
        assertEquals("one", info.getMatrixParameter().get(0).getKey());
        assertEquals("One", info.getMatrixParameter().get(0).getValue());
        assertEquals("two", info.getMatrixParameter().get(1).getKey());
        assertEquals(2, info.getMatrixParameter().get(1).getValue());
        assertEquals("three", info.getMatrixParameter().get(2).getKey());
        assertEquals("Three", info.getMatrixParameter().get(2).getValue());
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
