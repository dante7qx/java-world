package org.java.world.dante;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

/**
 * HAL Browser:  http://localhost:8080/explorer/index.html
 * 
 * @author dante
 *
 */
@EnableWebSocket
@SpringBootApplication
public class DanteGradleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DanteGradleApplication.class, args);
	}

}
