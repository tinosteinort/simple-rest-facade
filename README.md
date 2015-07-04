simple-rest-facade
==================
[![Build Status](https://api.shippable.com/projects/5597b3c0edd7f2c052585250/badge/master)]

Create dynamic and type-safe Clients for RESTful Webservices.

This Tool is helpful e.g. in the follwing Case: Server and Client shares the same Interfaces which are
 annotated with the `javax.ws.rs-api` Annotations.

There is no need to write Code that communicates with the RESTful Webservice. The `simple-rest-facade`
 creates a Proxy from the given Interface. If Interface Methods are called, the Proxy translates these
 into RESTful Service Calls.

Example Interface:
```java
@Path("personservice")
@Consumes(MediaType.APPLICATION_JSON)    
interface PersonService {
    
    @GET
    @Path("/person/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    Person getPersonByName(@PathParam("name") String name);
}
```

Client Code to call Service:
```java
public static void main(String[] args) {
    
    final HttpClient httpClient = HttpClientBuilder.create().build();
    final URI uri = URI.create("http://localhost:8080/my-app");
    
    // Create and configure Factory
    final RestFacadeFactory factory = new DefaultRestFacadeFactory(uri, httpClient, MediaType.APPLICATION_JSON);
    
    // Create a Proxy for the given Interface
    final PersonService personService = factory.createFacade(PersonService.class);
    
    final Person person = personService.getPersonByName("Donnie");
}
```

Possible Serverside Implementation:
```java
class PersonServiceServerSideImpl implements PersonService {
    
    @Override
    public Person getPersonByName(final String name) {
        final Person person = new Person();
        person.setFirstname(name);
        return person;
    }
}
```

It is possible to marshal the Objects into JSON or XML. Also custom marshalling is supported.
