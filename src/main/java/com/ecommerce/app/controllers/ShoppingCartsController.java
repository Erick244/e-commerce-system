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

import com.ecommerce.app.models.dto.shppingCart.AddProductInShoppingCartDto;
import com.ecommerce.app.models.dto.shppingCart.CreateShoppingCartDto;
import com.ecommerce.app.services.ShoppingCartsService;

@RestController
@RequestMapping("/shoppingCarts")
public class ShoppingCartsController {

	@Autowired
	private ShoppingCartsService shoppingCartsService;

	@PostMapping
	public ResponseEntity<?> create(@RequestBody CreateShoppingCartDto createShoppingCartDto) {
		return this.shoppingCartsService.create(createShoppingCartDto);
	}
	
	@GetMapping
	public ResponseEntity<?> findAll(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer take
	) {
		return this.shoppingCartsService.findAll(page, take);
	}
	
	@PostMapping("/checkout/{shoppingCartId}")
	public ResponseEntity<?> checkout(@PathVariable Integer shoppingCartId) {
		return this.shoppingCartsService.checkout(shoppingCartId);
	}
	
	@PostMapping("/addProduct")
	public ResponseEntity<?> addProduct(@RequestBody AddProductInShoppingCartDto addProductInShoppingCartDto) {
		return this.shoppingCartsService.addProduct(addProductInShoppingCartDto);
	}
}
