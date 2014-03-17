package de.tse.simplerestfacade.example.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import de.tse.simplerestfacade.JerseyRestFacadeFactory;
import de.tse.simplerestfacade.RestFacadeFactory;
import de.tse.simplerestfacade.example.consumer.api.Person;
import de.tse.simplerestfacade.example.consumer.api.UserService;
import de.tse.simplerestfacade.example.provider.RestServer;

public class RestClient {

	private final UserService userService;
	
	public RestClient(final String url) {
		
		final RestFacadeFactory factory = new JerseyRestFacadeFactory(url);
		this.userService = factory.createFacade(UserService.class, MediaType.APPLICATION_XML);
	}
	
	public void createPersons() {
		
		final List<Person> persons = Arrays.asList(new Person("Max", "M."), 
											 	   new Person("John", "D."),
												   new Person("Emma", "W."));
		
		for (Person person : persons) {
			
			final Person createdPerson = userService.createUser(person);
			System.out.println("created: " + createdPerson);
		}
	}
	
	public void loadAllPersons() {
		
		Person[] foundPersons = userService.findUser("Max", "M");
		for (Person person : foundPersons) {
			System.out.println("found: " + person);
		}
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		
		final RestServer server = new RestServer("http://localhost:8080/restexample");
		server.start();
		
		final RestClient client = new RestClient("http://localhost:8080/restexample");
		client.createPersons();
		client.loadAllPersons();
		
		server.stop();
	}
}
