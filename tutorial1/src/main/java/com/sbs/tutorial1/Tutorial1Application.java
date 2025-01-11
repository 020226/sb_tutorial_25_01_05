package com.sbs.tutorial1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // @EntityListeners 기능 사용하려면 필요
public class Tutorial1Application {

	public static void main(String[] args) {
		SpringApplication.run(Tutorial1Application.class, args);
	}

}
