package com.example.Mini1st;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class Mini1stApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mini1stApplication.class, args);
	}

}
