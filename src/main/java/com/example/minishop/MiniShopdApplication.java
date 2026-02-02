package com.example.minishop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MiniShopdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniShopdApplication.class, args);
//		System.out.println(new BCryptPasswordEncoder().encode("admin123"));

	}

}
