package de.tse.simplerestfacade.xml;

import java.io.StringReader;

import javax.xml.bind.JAXB;

import de.tse.simplerestfacade.marshalling.Unmarshaller;

public class JaxbUnmarshaller implements Unmarshaller {

    @Override public Object unmarshall(String string, Class<?> targetClass) {
        return JAXB.unmarshal(new StringReader(string), targetClass);
    }
}
