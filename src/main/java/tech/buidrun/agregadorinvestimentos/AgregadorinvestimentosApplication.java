package tech.buidrun.agregadorinvestimentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// FUNCIONANDO 
// https://youtu.be/Tnl4YnB6E54?list=PLxCh3SsamNs62j6T7bv6f1_1j9H9pEzkC
// [ADI #1] - Criando um CRUD com Java Spring Boot e MySQL

@SpringBootApplication
@EnableFeignClients
public class AgregadorinvestimentosApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgregadorinvestimentosApplication.class, args);
	}

}
