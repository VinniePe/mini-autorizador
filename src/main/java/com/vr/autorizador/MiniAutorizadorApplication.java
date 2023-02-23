package com.vr.autorizador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.vr")
public class MiniAutorizadorApplication {

	public static void main(String[] args) {
//		System.setProperty("spring.devtools.restart.enabled", "false");
		
		//Swagger http://localhost:8080/swagger-ui/index.html#/
		//MongoExpress http://localhost:8081//
		//RabbitMq http://localhost:8082/#/
		
		SpringApplication.run(MiniAutorizadorApplication.class, args);
	}

}
