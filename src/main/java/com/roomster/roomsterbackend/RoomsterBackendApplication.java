package com.roomster.roomsterbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication()
public class RoomsterBackendApplication {
	public String POST = System.getenv("POST");


	public static void main(String[] args) {
		SpringApplication.run(RoomsterBackendApplication.class, args);
	}

}