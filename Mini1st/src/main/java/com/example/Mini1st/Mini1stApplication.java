package com.example.Mini1st;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication//(exclude = DataSourceAutoConfiguration.class)
@MapperScan(value={"mybatis.dao.login", "com.example.Mini1st.dao"})
public class Mini1stApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mini1stApplication.class, args);
	}

}
