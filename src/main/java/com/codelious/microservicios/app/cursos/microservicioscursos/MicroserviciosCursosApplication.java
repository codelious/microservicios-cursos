package com.codelious.microservicios.app.cursos.microservicioscursos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@EntityScan({
			"com.codelious.microservicios.commons.alumnos.commonsalumnos.models.entity",
			"com.codelious.microservicios.commons.examenes.commonsexamenes.models.entity",
			"com.codelious.microservicios.app.cursos.microservicioscursos.models.entity"
		})
public class MicroserviciosCursosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviciosCursosApplication.class, args);
	}

}
