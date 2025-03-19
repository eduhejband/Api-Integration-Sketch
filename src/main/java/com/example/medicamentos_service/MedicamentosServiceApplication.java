package com.example.medicamentos_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.example.medicamentos_service.client")

public class MedicamentosServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MedicamentosServiceApplication.class, args);
	}
}
