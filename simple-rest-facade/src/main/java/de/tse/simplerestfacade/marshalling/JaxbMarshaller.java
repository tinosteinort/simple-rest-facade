package de.tse.simplerestfacade.marshalling;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXB;

public class JaxbMarshaller implements Marshaller {

    @Override
    public String marshall(Object data) {
        final String string;

        try (final StringWriter stringWriter = new StringWriter()) {
            JAXB.marshal(data, stringWriter);
            string = stringWriter.toString();
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        return string;
    }

}
