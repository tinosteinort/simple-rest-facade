package de.tse.simplerestfacade.examplecode;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import de.tse.simplerestfacade.DefaultRestFacadeFactory;
import de.tse.simplerestfacade.RestFacadeFactory;
import de.tse.simplerestfacade.data.Person;

public class Example {

    public static void main(String[] args) {

        final HttpClient httpClient = HttpClientBuilder.create().build();
        final URI uri = URI.create("http://localhost:8080/my-app");

        // Create and configure Factory
        final RestFacadeFactory factory = new DefaultRestFacadeFactory(uri, httpClient, MediaType.APPLICATION_JSON);

        // Create a Proxy for the given Interface
        final PersonService personService = factory.createFacade(PersonService.class);

        final Person person = personService.getPersonByName("Donnie");
    }
}

@Path("personservice")
@Consumes(MediaType.APPLICATION_JSON)    
interface PersonService {

    @GET
    @Path("/person/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Person getPersonByName(@PathParam("name") String name);
}

class PersonServiceServerSideImpl implements PersonService {

    @Override
    public Person getPersonByName(final String name) {
        final Person person = new Person();
        person.setFirstname(name);
        return person;
    }
}