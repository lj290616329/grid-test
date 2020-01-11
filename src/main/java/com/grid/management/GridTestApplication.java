package com.grid.management;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = {"com.grid.management.*.mapper","com.grid.management.manager.config.shior.session.dao.*"})
@ComponentScan(basePackages = {"com.grid.management.*"})
public class GridTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(GridTestApplication.class, args);
	}

}
