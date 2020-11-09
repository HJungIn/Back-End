package com.project.gonggus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class GonggusApplication {

	public static void main(String[] args) {
		SpringApplication.run(GonggusApplication.class, args);
	}

}
