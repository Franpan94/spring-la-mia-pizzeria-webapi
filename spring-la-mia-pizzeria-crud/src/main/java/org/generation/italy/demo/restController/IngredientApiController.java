package org.generation.italy.demo.restController;

import java.util.List;

import org.generation.italy.demo.pojo.Ingredient;
import org.generation.italy.demo.pojo.Pizzeria;
import org.generation.italy.demo.serv.IngredientService;
import org.generation.italy.demo.serv.PizzeriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/1/ingredient")
@CrossOrigin("*")
public class IngredientApiController {

	@Autowired
	private IngredientService ingredientService;
	
	@Autowired
	private PizzeriaService pizzaService;
	
	@GetMapping("/all")
	public List<Ingredient> index(){
		
		return ingredientService.findAll();
	}
	
	@GetMapping("/pizza/{id}")
	public List<Ingredient> getIngredientByPizzaId(@PathVariable("id") int id){
		
		Pizzeria pizza = pizzaService.findPizzaId(id).get();
		
		return pizza.getIngredients();
	}
}
