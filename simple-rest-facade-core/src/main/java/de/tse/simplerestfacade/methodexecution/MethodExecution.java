package de.tse.simplerestfacade.methodexecution;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;

import de.tse.simplerestfacade.methodinformation.MethodInformation;

public interface MethodExecution {
    
    Object execute(MethodInformation methodInformation) throws URISyntaxException, ClientProtocolException, IOException;
}