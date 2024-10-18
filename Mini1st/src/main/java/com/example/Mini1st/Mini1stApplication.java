package com.example.Mini1st;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
@MapperScan(value={"mybatis.dao", "com.example.Mini1st.mapper"})
public class Mini1stApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mini1stApplication.class, args);
	}

}
