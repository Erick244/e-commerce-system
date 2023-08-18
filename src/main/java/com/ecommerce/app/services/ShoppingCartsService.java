package com.ecommerce.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.app.exceptions.EcommerceException;
import com.ecommerce.app.exceptions.NoStockException;
import com.ecommerce.app.exceptions.NotFoundException;
import com.ecommerce.app.models.dto.shppingCart.AddProductInShoppingCartDto;
import com.ecommerce.app.models.dto.shppingCart.CreateShoppingCartDto;
import com.ecommerce.app.models.dto.shppingCart.ResponseCreateShoppingCartDto;
import com.ecommerce.app.models.entities.Product;
import com.ecommerce.app.models.entities.ShoppingCart;
import com.ecommerce.app.models.repositories.ProductRepository;
import com.ecommerce.app.models.repositories.ShoppingCartRepository;

@Service
public class ShoppingCartsService {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@Autowired
	private ProductRepository productRepository;

	public ResponseEntity<?> checkout(Integer shoppingCartId) {
		ShoppingCart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId).orElse(null);

		if (shoppingCart == null) {
			return ResponseEntity.badRequest().build();
		}

		for (Product product : shoppingCart.getProducts()) {

			product.discountStock();
			product.removeShoppingCart(shoppingCart);

			this.productRepository.save(product);
		}

		shoppingCart.resetTotal();
		shoppingCart.clearProducts();

		this.shoppingCartRepository.save(shoppingCart);

		return ResponseEntity.noContent().build();
	}

	public ResponseEntity<?> create(CreateShoppingCartDto createShoppingCartDto) {

		List<Integer> productIds = createShoppingCartDto.productIds();

		if (productIds.isEmpty() || productIds == null) {
			return ResponseEntity.badRequest().build();
		}

		try {

			ShoppingCart shoppingCart = new ShoppingCart();

			for (Integer productId : productIds) {
				Product product = this.productRepository.findById(productId).orElse(null);

				this.addBidirectionalProduct(product, shoppingCart);
			}

			return ResponseEntity.ok(new ResponseCreateShoppingCartDto(shoppingCart.getId()));

		} catch (EcommerceException exception) {
			return ResponseEntity.status(exception.status).body(exception.errorMessage);
		}
	}

	public ResponseEntity<?> findAll(Integer page, Integer take) {
		try {
			Boolean isPagedQuery = page != null && take != null;

			if (isPagedQuery) {
				Pageable pageable = PageRequest.of(page, take);

				Iterable<Product> shoppingCartsPage = this.productRepository.findAll(pageable);

				return ResponseEntity.ok(shoppingCartsPage);
			}

			Iterable<ShoppingCart> shoppingCarts = this.shoppingCartRepository.findAll();

			return ResponseEntity.ok(shoppingCarts);
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}

	public ResponseEntity<?> addProduct(AddProductInShoppingCartDto addProductInShoppingCartDto) {
		Integer shoppingCartId = addProductInShoppingCartDto.shoppingCartId();
		ShoppingCart shoppingCart = this.shoppingCartRepository.findById(shoppingCartId).orElse(null);

		List<Integer> productIds = addProductInShoppingCartDto.productIds();
		if (productIds.isEmpty() || productIds == null) {
			return ResponseEntity.badRequest().build();
		}

		try {

			for (Integer productId : productIds) {
				Product product = this.productRepository.findById(productId).orElse(null);

				this.addBidirectionalProduct(product, shoppingCart);
			}

			return ResponseEntity.ok(new ResponseCreateShoppingCartDto(shoppingCart.getId()));

		} catch (EcommerceException exception) {
			return ResponseEntity.status(exception.status).body(exception.errorMessage);
		}
	}

	private void addBidirectionalProduct(Product product, ShoppingCart shoppingCart) {
		if (product == null) {
			throw new NotFoundException();
		}

		if (product.getStock() == 0) {
			throw new NoStockException();
		}

		shoppingCart.addProduct(product);
		this.shoppingCartRepository.save(shoppingCart);

		product.addShoppingCart(shoppingCart);
		this.productRepository.save(product);
	}
}
