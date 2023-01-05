package com.ingsw.ratatouille;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class RatatuilleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatatuilleApplication.class, args);
	}

	
}
