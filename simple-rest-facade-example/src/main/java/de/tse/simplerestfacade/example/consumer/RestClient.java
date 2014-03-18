package de.tse.simplerestfacade.example.consumer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.MediaType;

import de.tse.simplerestfacade.JerseyRestFacadeFactory;
import de.tse.simplerestfacade.RestFacadeFactory;
import de.tse.simplerestfacade.example.consumer.api.User;
import de.tse.simplerestfacade.example.consumer.api.UserService;
import de.tse.simplerestfacade.example.provider.RestServer;

public class RestClient {

	private final UserService userService;
	
	public RestClient(final String url) {
		
		final RestFacadeFactory factory = new JerseyRestFacadeFactory(url);
		this.userService = factory.createFacade(UserService.class, MediaType.APPLICATION_XML);
	}
	
	public void createUsers() {
		
		final List<User> users = Arrays.asList(new User("Max", "M."), 
											   new User("John", "D."),
											   new User("Emma", "W."));
		
		for (User user : users) {
			
			final User createdUser = userService.createUser(user);
			System.out.println("created: " + createdUser);
		}
	}
	
	public void loadAllUsers() {
		
		final User[] foundUsers = userService.findUser("Max", "M");
		for (User user : foundUsers) {
			System.out.println("found: " + user);
		}
	}
	
	public static void main(String[] args) throws IllegalArgumentException, IOException {
		
		final RestServer server = new RestServer("http://localhost:8080/restexample");
		server.start();
		
		final RestClient client = new RestClient("http://localhost:8080/restexample");
		client.createUsers();
		client.loadAllUsers();
		
		server.stop();
	}
}
