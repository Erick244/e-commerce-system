package com.ecommerce.app.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ecommerce.app.models.entities.ShoppingCart;

public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer>, PagingAndSortingRepository<ShoppingCart, Integer> {

}
