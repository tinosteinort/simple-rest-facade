package de.tse.simplerestfacade.example.provider.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.tse.simplerestfacade.example.consumer.api.Person;

@Path("/persons")
public class PersonServiceImpl {

	private static final Map<Integer, Person> persons = new HashMap<>();
	
	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_XML)
	public Person createPerson(final Person person) {
		person.setId(persons.size());
		persons.put(person.getId(), person);
		return person;
	}

	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Person getPerson(@PathParam("id") final Integer id) {
		return persons.get(id);
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public void deletePerson(@PathParam("id") final Integer id) {
		persons.remove(id);
	}

	@GET
	@Path("/find")
	@Produces(MediaType.APPLICATION_XML)
	public Person[] findPerson(@QueryParam("firstname") final String firstname, @QueryParam("lastname") final String lastname) {
		final List<Person> matchingPersons = new ArrayList<>();
		
		for (Person person : persons.values()) {
			
			boolean firstnameMatch = false;
			if (firstname == null || (firstname != null && firstname.equals(""))) {
				firstnameMatch = true;
			}
			else {
				firstnameMatch = person.getFirstname().startsWith(firstname);
			}
			
			boolean lastnameMatch = false;
			if (lastname == null || (lastname != null && lastname.equals(""))) {
				lastnameMatch = true;
			}
			else {
				lastnameMatch = person.getLastname().startsWith(lastname);
			}
			
			if (firstnameMatch || lastnameMatch) {
				matchingPersons.add(person);
			}
		}
		
		return matchingPersons.toArray(new Person[0]);
	}
}
