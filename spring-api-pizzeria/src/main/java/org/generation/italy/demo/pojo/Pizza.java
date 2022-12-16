package org.generation.italy.demo.pojo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Pizza {
     
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message="Il nome deve contenere qualcosa")
	@Size(min=3, message="Il nome deve contenere almeno 3 caratteri")
	@Column(unique = true)
	private String name;
	
	@NotNull(message="Il prezzo deve avere un valore compreso tra 6 e 30")
	@Min(value=6, message="Il prezzo deve avere un valore maggiore o uguale a 6")
	@Max(value=30, message="Il prezzo deve avere un valore minore o uguale a 30")
	@Column
	private Integer price;
	
	@NotEmpty(message="La descrizione deve contenere qualcosa")
	@Size(min=5, message="La descrizione deve contenere almeno 5 caratteri")
	@Column
	private String description;
	
	public Pizza() {
		
	}
	
    public Pizza(String name, int price, String description) {
		
		setName(name);
		setPrice(price);
		setDescription(description);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
