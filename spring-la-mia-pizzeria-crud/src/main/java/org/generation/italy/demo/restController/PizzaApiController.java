package org.generation.italy.demo.restController;

import java.util.List;
import org.generation.italy.demo.pojo.Pizzeria;
import org.generation.italy.demo.serv.PizzeriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/1/pizza")
@CrossOrigin("*")
public class PizzaApiController {

	@Autowired 
	private PizzeriaService pizzeriaService;
	
	@GetMapping("/all")
    public List<Pizzeria> getPizze(){
		
		List<Pizzeria> pizze = pizzeriaService.findAll();
		
		return pizze;
	}
	
}
