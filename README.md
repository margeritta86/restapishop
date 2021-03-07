# REST API Small Shop


### BASIC PART

- zwraca listę produktów ( nazwa, cena, obrazek ) 
- zwraca szczegóły produktu ( szczegóły, lista kilku atrybutów, ocena produktu ) 
- dodaje produkt do koszyka 
- zwraca dane o koszyku 
- pozwala na operacje na koszyku ( zwiększania ilości, usuwania produktów, ustawiania kodu rabatowego ) 
- ustawia w koszyku podstawowe dane użytkownika ( imię, nazwisko, adres dostawy ) 
- pozwala złożyć zamówienie na koszyk ( zakładamy płatność przy odbiorze ) 


### ADDITIONAL PART

- powiązać koszyk z użytkownikiem 
- zwracać listę zamówień danego użytkownika 
- dodać filtrowanie listingu po cenie i słowach kluczowych
- rozszerzyć produkty o stan magazynowy, dostępność produktu do kupienia
- zabezpieczyć koszyk, żeby nie dało się złożyć zamówienia na produkt ponad jego stan magazynowy
- przeliczyć wartość koszyka w zależności od ustawionego kodu rabatowego

# REST API ENDPOINTS

* `GET /api/products` - get list of all products ( with name, price and imageUrl )
* `GET /api/products/{id}` - get details, list of attributes, rate of the product by id
* `POST /api/baskets/{basketId}/products/{id}`- add product to basket ( identified basket's id and product's id )
* `GET /api/baskets/{id}` - get basket data ( identified by basket's id ) 
* `PATCH /api/baskets/{id}/products/{id}` - update product amount ( identified basket's id and product's id )
* `DELETE /api/baskets/{id}/products/{id}` - delete product from basket ( identified basket's id and product's id )
* `PATCH /api/baskets/{id}/discount` - set a discount code to basket ( identyfied by basket's id )
* `POST /api/baskets/{id}/customers` - set basic customer's data in the basket ( identyfied by basket's id )
* `POST /api/orders/baskets/{id}` - place order for the basket ( identyfied by basket's id )
* `POST /api/orders/baskets/{basketId}/customers/{customerId}` - place order for the basket with the customer ( identyfied by basket's id and customer's id )
* `GET /api/customers/{id}/orders` - get list of all customer's orders ( identyfied by customer's id )
* `GET /api/products/byPrice?price={price}&minOrMax={min or max}` - get list of products by minimal or maximum price
* `GET /api/products/byKeyword?keyword=ham` - get list of products by keyword




