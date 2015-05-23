package de.tse.simplerestfacade.marshalling;

import java.io.StringReader;

import javax.xml.bind.JAXB;

public class JaxbUnmarshaller implements Unmarshaller {

    @Override
    public Object unmarshall(String string, Class<?> targetClass) {
        return JAXB.unmarshal(new StringReader(string), targetClass);
    }
}
