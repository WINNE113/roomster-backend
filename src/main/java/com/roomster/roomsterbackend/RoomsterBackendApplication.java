package com.roomster.roomsterbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class RoomsterBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomsterBackendApplication.class, args);
	}

}
