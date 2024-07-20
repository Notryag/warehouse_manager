package com.pn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.pn.mapper"})
public class WarehouseManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WarehouseManagerApplication.class, args);
	}

}
