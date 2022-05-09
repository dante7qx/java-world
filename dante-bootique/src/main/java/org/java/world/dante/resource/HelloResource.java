package org.java.world.dante.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.java.world.dante.service.IHelloService;
import org.java.world.dante.vo.UserVO;

import com.google.inject.Inject;

@Path("")
public class HelloResource {

	@Inject
	private IHelloService helloService;

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN + ";charset=utf-8")
	public String hello() {
		return helloService.sayHello();
	}

	@GET
	@Path("/users")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserVO> getUsers() {
		return helloService.getUserList();
	}

	@GET
	@Path("/xml_users")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_XML)
	public List<UserVO> getXmlUsers() {
//		GenericEntity<List<UserVO>> result = new GenericEntity<List<UserVO>>(helloService.getUserList()) {};
		return helloService.getUserList();
	}
	
	@GET
	@Path("/xhtml_users")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_XHTML_XML)
	public List<UserVO> xhtmlUsers() {
		return helloService.getUserList();
	}

}
