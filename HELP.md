# Electronic Store Checkout API

The Electronic Store Checkout API provides endpoints for adding/deleting products, basket operations
and generating receipts for a web-based electronics store.

## Admin User Operations

### Create a new product

- Endpoint: `POST /products`
- Description: Creates a new product in the store.
- Request Body:
    - `name` (string): The name of the product.
    - `price` (number): The price of the product.
- Response:
    - `200 OK` if the product is created successfully.
    - `400 Bad Request` if the request body is invalid.

### Remove a product

- Endpoint: `DELETE /products/{productId}`
- Description: Removes a product from the store.
- Parameters:
    - `productId` (integer): The ID of the product to be removed.
- Response:
    - `200 OK` if the product is removed successfully.
    - `404 Not Found` if the product with the specified ID is not found.

### Add discount deals for products

- Endpoint: `POST /deals`
- Description: Adds a discount deal for a product in the store.
- Request Body:
    - `productId` (integer): The ID of the product to apply the deal.
    - `dealType` (string): The type of deal to be applied.
- Response:
    - `200 OK` if the deal is added successfully.
    - `404 Not Found` if the product with the specified ID is not found.

## Customer Operations

### Add a product to the basket

- Endpoint: `POST /basket/{productId}`
- Description: Adds a product to the customer's basket.
- Parameters:
    - `productId` (integer): The ID of the product to add to the basket.
    - `customerId` (string): The ID of the customer.
- Response:
    - `200 OK` if the product is added to the basket successfully.
    - `404 Not Found` if the product with the specified ID is not found.

### Remove a product from the basket

- Endpoint: `DELETE /basket/{productId}`
- Description: Removes a product from the customer's basket.
- Parameters:
    - `productId` (integer): The ID of the product to remove from the basket.
    - `customerId` (string): The ID of the customer.
- Response:
    - `200 OK` if the product is removed from the basket successfully.
    - `404 Not Found` if the product with the specified ID is not found.

### Calculate receipt

- Endpoint: `GET /receipt`
- Description: Calculates the receipt for the customer's basket.
- Parameters:
    - `customerId` (string): The ID of the customer.
- Response:
    - `200 OK` with the receipt details in the response body.
    - `404 Not Found` if the customer with the specified ID is not found.

---

### To start the application
To start the application, use the following command:

```bash
mvn spring-boot:run
```

### Running the Tests
To run the tests, use below command:

```bash
mvn test
```



