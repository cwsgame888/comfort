package com.comfort.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class ComfortProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComfortProjectApplication.class, args);
	}

}
