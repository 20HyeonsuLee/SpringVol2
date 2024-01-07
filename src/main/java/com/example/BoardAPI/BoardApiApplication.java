package com.example.BoardAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BoardApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(BoardApiApplication.class, args);
	}
}
