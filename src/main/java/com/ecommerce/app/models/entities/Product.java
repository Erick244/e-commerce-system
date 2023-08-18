package com.ecommerce.app.models.entities;

import java.util.ArrayList;
import java.util.List;

import com.ecommerce.app.models.dto.product.CreateProductDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull()
	@Size(max = 100, min = 3)
	private String name;
	
	@NotNull()
	@Size(max = 250, min = 10)
	private String description;
	
	@Min(0)
	@NotNull()
	private Double price;
	
	@Min(0)
	@NotNull()
	private Integer stock;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	private List<ShoppingCart> shoppingCarts = new ArrayList<>();
	
	public Product() {
	}
	
	public Product(CreateProductDto createProductDto) {
		name = createProductDto.name();
		description = createProductDto.description();
		price = createProductDto.price();
		stock = createProductDto.stock();
	}

	public Product(String name, String description, @Min(0) Double price, @Min(0) Integer stock) {
		super();
		this.name = name;
		this.description = description;
		this.price = price;
		this.stock = stock;
	}
	
	public void discountStock() {
		setStock(stock--);
	}
	
	public void removeShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCarts.remove(shoppingCart);
	}
	
	public void addShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCarts.add(shoppingCart);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getId() {
		return id;
	}
}
