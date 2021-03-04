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

* `GET http://localhost:8080/api/products` - get list of all products (with name, price and imageUrl)
* `GET http://localhost:8080/api/products/{id}` - get details, list of attributes, rate of the product by id
* `POST http://localhost:8080/api/baskets//{basketId}/products/{id}`- add product to basket (identified basket's id and product's id)
* `GET http://localhost:8080/api/baskets/{id}` - get basket data (identified by basket's id) 
* `PATCH http://localhost:8080/api/baskets/{id}/products/{id}` - update product amount (identified basket's id and product's id)
* `DELETE http://localhost:8080/api/baskets/{id}/products/{id}` - delete product from basket (identified basket's id and product's id)
* `PATCH http://localhost:8080/api/baskets/{id}` - set a discount code to basket (identyfied by basket's id)
* `POST http://localhost:8080/api/baskets/{id}/users` - set basic customer's data in the basket (identyfied by basket's id)
* `POST http://localhost:8080/api/baskets/{id}/orders` - place order for the basket (identyfied by basket's id)
* `GET http://localhost:8080/api/users/{id}` - get list of all customer's orders (identyfied by customer's id)
* `GET http://localhost:8080/api/products/byPrice?price={price}&minOrMax={min or max}` - get list of products by minimal or maximum price
* `GET http://localhost:8080/api/products/byKeyword?keyword=ham` - get list of products by keyword



