## API ROUTES

* **Create product**: `POST /products`
    * Request Body Model:
      ```json
        {
          "name": "productName",
          "price": 999,
          "description": "productDescription",
          "stock": 999
        }

* **Find all products: Paged**: `GET /products?page=999&take=999`, All: `GET /products`
  
* **Find product by ID**: `POST /products/findById/[productId]`
  
* **Create shopping cart**: `POST /shoppingCarts`
  * Request Body Model:
    ```json
      {
        "productIds": [1, 2, 3]
      }

* **Find all shopping carts:  Paged**: `GET /shoppingCarts?page=999&take=999`, All: `GET /shoppingCarts`
  
* **Add product in shopping cart**: `POST /shoppingCarts/addProduct`
    * Request Body Model:
      ```json
        {
          "shoppingCartId": 999,
          "productIds": [1]
        }

* **Checkout shopping cart**: `POST /shoppingCarts/checkout/[shoppingCartId]`
