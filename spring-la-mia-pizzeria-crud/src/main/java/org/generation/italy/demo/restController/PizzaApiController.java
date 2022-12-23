package org.generation.italy.demo.restController;

import java.util.List;
import org.generation.italy.demo.pojo.Pizzeria;
import org.generation.italy.demo.serv.PizzeriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


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
	
	@PostMapping("/create")
	public Pizzeria create(@Valid @RequestBody Pizzeria pizza) {
		
		Pizzeria newPizza = pizzeriaService.save(pizza);
		
		return newPizza;
		
	}
	
	@PostMapping("/edit/{id}")
	public Pizzeria edit(@PathVariable("id") int id, 
			             @Valid @RequestBody Pizzeria pizza) {
							
		Pizzeria oldPizza = pizzeriaService.findPizzaId(id).get();
		pizza.setIngredients(oldPizza.getIngredients());
		
		Pizzeria newPizza = pizzeriaService.save(pizza);
		
		return newPizza;
		
	}
	
	@GetMapping("/delete/{id}")
	public boolean delete(@PathVariable("id") int id) {
		
		 pizzeriaService.deleteById(id);
		 return true;
		
	}
	
	@GetMapping("/search/{name}")
	public List<Pizzeria> searchByName(@PathVariable("name") String name) {
		
		List<Pizzeria> pizze = null;
		
		if(name == null) pizze = pizzeriaService.findAll();
		else pizze = pizzeriaService.findByName(name);
		
		return pizze;
	}
}
