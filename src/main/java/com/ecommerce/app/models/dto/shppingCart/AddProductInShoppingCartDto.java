package com.ecommerce.app.models.dto.shppingCart;

import java.util.List;

public record AddProductInShoppingCartDto(Integer shoppingCartId, List<Integer> productIds) {

}
