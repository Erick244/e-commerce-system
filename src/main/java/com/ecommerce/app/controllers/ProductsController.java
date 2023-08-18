package com.ecommerce.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.app.models.dto.product.CreateProductDto;
import com.ecommerce.app.models.entities.Product;
import com.ecommerce.app.services.ProductsService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/products")
public class ProductsController {

	@Autowired
	private ProductsService productsService;
	
	@PostMapping
	public ResponseEntity<?> create(@RequestBody @Valid CreateProductDto createProductDto) {
		return this.productsService.create(createProductDto);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer take
	) {
		return this.productsService.findAll(page, take);
	}
	
	@GetMapping("/findById/{id}")
	public ResponseEntity<Product> findOne(@PathVariable int id) {
		return this.productsService.findById(id);
	}
}
