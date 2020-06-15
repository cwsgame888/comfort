package com.pos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.core", "com.pos"})
public class PosProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(PosProjectApplication.class, args);
	}
}