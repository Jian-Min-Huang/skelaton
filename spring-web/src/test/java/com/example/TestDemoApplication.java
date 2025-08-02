package com.example;

import org.springframework.boot.SpringApplication;

public class TestDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringWebApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
