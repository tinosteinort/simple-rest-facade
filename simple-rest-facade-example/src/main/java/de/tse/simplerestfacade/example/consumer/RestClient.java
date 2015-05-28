package de.tse.simplerestfacade.example.consumer;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import de.tse.simplerestfacade.DefaultRestFacadeFactory;
import de.tse.simplerestfacade.RestClientException;
import de.tse.simplerestfacade.RestFacadeFactory;
import de.tse.simplerestfacade.example.consumer.api.User;
import de.tse.simplerestfacade.example.consumer.api.UserService;
import de.tse.simplerestfacade.example.provider.RestServer;

public class RestClient {

	private final UserService userService;
	
	public RestClient(final String url) {
		
	    final HttpClient httpClient = HttpClientBuilder.create().build();
	    
		final RestFacadeFactory factory = new DefaultRestFacadeFactory(URI.create(url), httpClient, MediaType.APPLICATION_XML);
		this.userService = factory.createFacade(UserService.class, MediaType.APPLICATION_XML);
	}
	
	public void createUsers() {
		
		final List<User> users = Arrays.asList(new User("Max", "M."), 
												new User("John", "D."),
												new User("Emma", "W."));
		
		System.out.println("Create users...");
		for (User user : users) {
			
			final User createdUser = userService.createUser(user);
			System.out.println("  " + createdUser);
		}
	}
	
	public void findUsers(final String firstname, final String lastname) {
		
		System.out.println("Searching for '" + firstname + " " + lastname + "' ... ");
		
		final User[] foundUsers = userService.findUser(firstname, lastname);
		for (User user : foundUsers) {
			System.out.println("  " + user);
		}
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		
		final RestServer server = new RestServer("http://localhost:8080/restexample");
		server.start();
		
		try {
			final RestClient client = new RestClient("http://localhost:8080/restexample");
			client.createUsers();
			client.findUsers("Max", "M");
		}
		catch (RestClientException ex) {
			System.err.println("Es ist ein Fehler aufgetreten, HTTP ReturnCode " + ex.getHttpResponseCode() + " :" + ex.getMessage());
		}
		
		server.stop();
	}
}
