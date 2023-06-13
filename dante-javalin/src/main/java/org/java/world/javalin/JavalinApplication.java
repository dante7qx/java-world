package org.java.world.javalin;

import io.javalin.Javalin;

/**
 * 官方文档：https://javalin.io/documentation
 * 
 * @author dante
 *
 */
public class JavalinApplication {

	public static void main(String[] args) {
		var app = Javalin.create()
	            .get("/", ctx -> ctx.result("Hello World"))
	            .start(7070);
		
		app.get("/hello/{name}", ctx -> { 
		    ctx.result("Hello: " + ctx.pathParam("name"));
		});
		app.get("/hello/<name>", ctx -> { 
		    ctx.result("Hello: " + ctx.pathParam("name"));
		});
		System.out.println("程序启动，端口：" + app.port());
	}

}
