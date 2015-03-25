package com.vladkel.rest.easy.example;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
 
@Path("/xml/user")
public class XMLService {
 
	@GET
	@Path("/get")
	@Produces("application/xml")
	public User getUserInXML() {
 
		User user = new User();
		user.setUsername("mkyong");
		user.setPassword("password");
		user.setPin(123456);
 
		return user; 
 
	}
	
	@GET
	@Path("/getAll")
	@Produces("application/xml")
	public Users getUsersInXML() {
 
		Users users = new Users();
		List<User> list = new ArrayList<User>();
		
		User user = new User();
		user.setUsername("mkyong");
		user.setPassword("password");
		user.setPin(123456);
		list.add(user);
		
		User user2 = new User();
		user2.setUsername("mkyong");
		user2.setPassword("password");
		user2.setPin(123456);
		list.add(user2);
		
		User user3 = new User();
		user3.setUsername("mkyong");
		user3.setPassword("password");
		user3.setPin(123456);
		list.add(user3);
 
		users.setUsers(list);
		return users; 
 
	}
 
}
