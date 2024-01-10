package com.master.productpulse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ProductPulseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductPulseApplication.class, args);
	}

}
