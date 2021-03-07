package com.orka.restapishop.service;

import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.excepiton.*;
import com.orka.restapishop.model.*;
import com.orka.restapishop.repository.*;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;


@Service
public class BasketService {

    private BasketRepository basketRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private DiscountCodeRepository discountCodeRepository;
    private DeliveryDataService deliveryDataService;


    public BasketService(BasketRepository basketRepository, CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository, DiscountCodeRepository discountCodeRepository, DeliveryDataService deliveryDataService) {
        this.basketRepository = basketRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.discountCodeRepository = discountCodeRepository;
        this.deliveryDataService = deliveryDataService;
    }

    public void saveProductToBasket(long basketId, long productId, int amount) {

        Product product = findProductById(productId);
        Basket basket = findBasketById(basketId);
        basket.addProduct(product, amount);
        basketRepository.save(basket);

    }

    @PostConstruct //wypełniam bazę danych testowymi danymi
    public void fillDataBase() {

        if (basketRepository.count() > 0 && customerRepository.count() > 0) {
            return;
        }

        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();

        Address address = new Address("Wrocław", "Grunwaldzka", "15", "20", "70-233", "Polska");
        DeliveryData deliveryData = new DeliveryData(address, "Małgosia", "Kowalska", "malgosia@gmail.com");
        deliveryData = deliveryDataService.createAndSaveDeliveryData(deliveryData.mapToDto());
        customer1.setDeliveryData(deliveryData);

        Basket basket1 = new Basket();
        Basket basket2 = new Basket();
        Basket basket3 = new Basket();
        Basket basket4 = new Basket();

        basketRepository.save(basket1);
        basketRepository.save(basket2);
        basketRepository.save(basket3);

        basketRepository.save(basket4);

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        customer1.setBasket(basket1);
        customer2.setBasket(basket2);
        customer3.setBasket(basket3);

        customerRepository.save(customer1);
        customerRepository.save(customer2);
        customerRepository.save(customer3);

        basketRepository.save(basket1);
        basketRepository.save(basket2);
        basketRepository.save(basket3);

    }

    public BasketDto getBasketDtoById(Long id) {
        return findBasketById(id)
                .mapToDto();
    }


    public void updateBasketProducts(BasketDto basketDto, Long basketId) {
        Basket basket = findBasketById(basketId);
        updateProductAmount(basketDto, basket);
        basketRepository.save(basket);
    }

    private void updateProductAmount(BasketDto basketDto, Basket basket) {
        if (basketDto.getProducts() == null) {
            return;
        }
        for (Map.Entry<Long, Integer> entry : basketDto.getProducts().entrySet()) {
            Long productId = entry.getKey();
            int requestedAmount = entry.getValue();
            Product product = findProductById(productId);
            basket.updateProduct(product, requestedAmount);
        }
    }

    public void updateBasketDiscountCode(BasketDto basketDto, Long basketId) {
        Basket basket = findBasketById(basketId);
        updateDiscountCode(basketDto, basket);
        basketRepository.save(basket);
    }

    private void updateDiscountCode(BasketDto basketDto, Basket basket) {
        String discountCode = basketDto.getDiscountCode();
        if (discountCode == null) {
            return;
        } else if (basket.getDiscountCode() != null && discountCode.equals(basket.getDiscountCode().getName())) {
            return;
        }
        applyDiscount(basketDto, basket);
    }

     void applyDiscount(BasketDto basketDto, Basket basket) {
        DiscountCode discount = discountCodeRepository.findByName(basketDto.getDiscountCode().toLowerCase()).orElseThrow();
        basket.setDiscountCode(discount);
    }


    public void deleteProductFromBasket(Long basketId, Long productId) {
        Basket basket = findBasketById(basketId);
        basket.deleteProduct(findProductById(productId));
        basketRepository.save(basket);
    }

    public void setDeliveryData(Long basketId, DeliveryDataDto deliveryDataDto) {
        Basket basket = findBasketById(basketId);
        DeliveryData deliveryData = deliveryDataService.createAndSaveDeliveryData(deliveryDataDto);
        basket.setDeliveryData(deliveryData);
        basketRepository.save(basket);

    }

    public void placeOrder(Long basketId, Optional<Long> customerId) {
        Basket basket = findBasketById(basketId);
        Order order = new Order(basket);
        Customer customer = null;
        if (customerId.isPresent()) {
            customer = findCustomerById(customerId.get());
            if (basket.getDeliveryData() == null) {
                basket.setDeliveryData(customer.getDeliveryData());
            }
        }
        if (basket.getDeliveryData() == null) {
            throw new DeliveryDataRequiredException();
        }
        if (basket.getProducts().size() == 0) {
            throw new OrderingEmptyBasketException(basketId);
        }
        updateStore(basket);
        orderRepository.save(order);
        if (customer != null) {
            Basket newEmptyBasket = new Basket();
            basketRepository.save(newEmptyBasket);
            customer.setBasket(newEmptyBasket);
            customer.addOrderToList(order);
            customerRepository.save(customer);
        }
        basketRepository.save(basket);

    }

    private void updateStore(Basket basket) {
        Map<Product, Integer> products = basket.getProducts();
        for (Product product : products.keySet()) {
            product.decreaseAmount(products.get(product));
        }
        for (Product product : products.keySet()) {
            productRepository.save(product);
        }
    }

    public BasketDto recalculateBasketTotalPrice(Long basketId) {
        Basket basket = findBasketById(basketId);
        basket.calculateTotalPrice();
        basketRepository.save(basket);
        return basket.mapToDto();
    }

     Basket findBasketById(Long basketId) {
        return basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
    }

     Product findProductById(long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
    }

     Customer findCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }
}
