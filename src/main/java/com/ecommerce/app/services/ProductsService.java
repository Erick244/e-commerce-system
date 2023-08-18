package com.ecommerce.app.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ecommerce.app.models.dto.product.CreateProductDto;
import com.ecommerce.app.models.entities.Product;
import com.ecommerce.app.models.repositories.ProductRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@Service
public class ProductsService {

	@Autowired
	private ProductRepository productRepository;
	
	public ResponseEntity<?> create(CreateProductDto createProductDto) {
		try {
			Product product = new Product(createProductDto);
			
			this.productRepository.save(product);
			
			return ResponseEntity.noContent().build();
		} catch(ConstraintViolationException exception) {

			return ResponseEntity.badRequest().body(cleanValidationException(exception));
		}
	}
	
	public ResponseEntity<?> findAll(Integer page, Integer take) {
		try {
			Boolean isPagedQuery = page != null && take != null;
			
			if (isPagedQuery) {
				Pageable pageable = PageRequest.of(page, take);
				
				Iterable<Product> productsPage = this.productRepository.findAll(pageable);
				
				return ResponseEntity.ok(productsPage);
			}
			
			Iterable<Product> products = this.productRepository.findAll();
			
			return ResponseEntity.ok(products);
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
	}
	
	public ResponseEntity<Product> findById(Integer id) {
		Product product = this.productRepository.findById(id).orElse(null);
		
		if (product == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(product);
	}
	
	private Map<String, String> cleanValidationException(ConstraintViolationException exception) {
		Map<String, String> errors = new HashMap<>();
		Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
		
		for(ConstraintViolation<?> violation : violations) {
			
			String validationFieldPath = violation.getPropertyPath().toString();
			
			errors.put(validationFieldPath, violation.getMessage());
		}
		
		return errors;
	}
}
