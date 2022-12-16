package org.generation.italy.demo;

import org.generation.italy.demo.pojo.Pizza;
import org.generation.italy.demo.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringApiPizzeriaApplication implements CommandLineRunner {

	@Autowired
	private PizzaService pizzaService;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringApiPizzeriaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Pizza p1 = new Pizza("Margherita", 6, "Una pizza molto semplice");
		Pizza p2 = new Pizza("Capricciosa", 10, "Una pizza abbondante e pesante");
		Pizza p3 = new Pizza("Diavola", 8, "Una pizza molto piccante");
		
		pizzaService.save(p1);
		pizzaService.save(p2);
		pizzaService.save(p3);
		
	}

}
