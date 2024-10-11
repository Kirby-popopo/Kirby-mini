package com.example.Mini1st;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
@MapperScan(value={"mybatis.dao"})
public class Mini1stApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mini1stApplication.class, args);
	}

}