package de.tse.simplerestfacade.example.consumer.api;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/persons")
public interface UserService {

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_XML)
	User createUser(User user);
	
	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_XML)
	User getUser(@PathParam("id") Integer id);
	
	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_XML)
	void deleteUser(@PathParam("id") Integer id);
	
	@GET
	@Path("/find")
	@Produces(MediaType.APPLICATION_XML)
	User[] findUser(@QueryParam("firstname") String firstname, @QueryParam("lastname") String lastname);
}
