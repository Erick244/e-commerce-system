package com.ecommerce.app.models.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;

@Entity(name = "shopping_carts")
public class ShoppingCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Min(0)
	private Double total = 0.0;
	
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "shoppingCarts")
	@Column()
	private List<Product> products = new ArrayList<>();

	public ShoppingCart() {
	}
	
	public ShoppingCart(@Min(0) Double total, List<Product> products) {
		super();
		this.total = total;
		this.products = products;
	}

	public void addProduct(Product product) {
		
		products.add(product);
		
		total += product.getPrice();
	}
	
	public void resetTotal() {
		total = 0.0;
	}
	
	public void clearProducts() {
		products.clear();
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Integer getId() {
		return id;
	}
}
