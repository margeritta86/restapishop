package com.orka.restapishop.service;


import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.excepiton.*;
import com.orka.restapishop.model.*;
import com.orka.restapishop.repository.BasketRepository;
import com.orka.restapishop.repository.CustomerRepository;
import com.orka.restapishop.repository.OrderRepository;
import com.orka.restapishop.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;


@Service
public class BasketService {

    private BasketRepository basketRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private OrderRepository orderRepository;
    private DeliveryDataService deliveryDataService;


    public BasketService(BasketRepository basketRepository, CustomerRepository customerRepository, ProductRepository productRepository, OrderRepository orderRepository, DeliveryDataService deliveryDataService) {
        this.basketRepository = basketRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.deliveryDataService = deliveryDataService;
    }


    public void saveProductToBasket(long basketId, long productId, int amount) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));

        basket.addProduct(product, amount);
        basketRepository.save(basket);

    }

    @PostConstruct
    public void fillDataBase() {

        if (basketRepository.count() > 0 && customerRepository.count() > 0) {
            return;
        }

        //todo przygotowac razem z deliverydataa

        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();

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

        Order order1 = new Order();
        Order order2 = new Order();

        orderRepository.save(order1);
        orderRepository.save(order2);

        customer1.addOrderToList(order1);
        customer2.addOrderToList(order2);

        order1.setBasket(customer1.getBasket());
        order2.setBasket(customer2.getBasket());
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        orderRepository.save(order1);
        orderRepository.save(order2);

    }

    public BasketDto getBasketDtoById(Long id) {

        return basketRepository.findById(id).orElseThrow(() -> new BasketNotFoundException(id))
                .mapToDto();

    }


    public void updateBasket(BasketDto basketDto) {
        long id = basketDto.getId();
        Basket basket = basketRepository.findById(id).orElseThrow(() -> new BasketNotFoundException(id));
        boolean codeUpdated = updateDiscountCode(basketDto, basket);
        if (!codeUpdated) {
            updateProductAmount(basketDto, basket);
        }
        basketRepository.save(basket);
    }

    private boolean updateDiscountCode(BasketDto basketDto, Basket basket) {
        String discountCode = basketDto.getDiscountCode();
        if (discountCode == null || discountCode.isBlank() || discountCode.equals(basket.getDiscountCode())) {
            return false;
        }
        basket.setDiscountCode(discountCode);
        return true;
    }

    private void updateProductAmount(BasketDto basketDto, Basket basket) {
        basketDto.getProducts().entrySet().stream().forEach(entry -> {
            Long productId = entry.getKey();
            int requestedAmount = entry.getValue();
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
            if (requestedAmount > product.getAmount() || requestedAmount <= 0) {
                throw new RequestedAmountException(createAmountMessageException(productId, requestedAmount));
            }
            basket.updateProduct(product, requestedAmount);
        });
    }


    private String createAmountMessageException(long idProduct, int amount) {
        String message = "";

        if (amount <= 0) {
            message = "Requested amount: " + amount + "is too low.";
        } else {
            message = "inventory value: " + amount + " exceeded ";
        }

        message += " for product id" + idProduct;

        return message;
    }

    public void deleteProductFromBasket(Long basketId, Long productId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
        basket.deleteProduct(productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId)));
        basketRepository.save(basket);
    }

    public void setDeliveryData(Long basketId, DeliveryDataDto deliveryDataDto) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
        DeliveryData deliveryData = deliveryDataService.createAndSaveDeliveryData(deliveryDataDto);
        basket.setDeliveryData(deliveryData);
        basketRepository.save(basket);
    }

    public void placeOrder(Long basketId, Optional<Long> customerId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
        Order order = new Order(basket);
        if (basket.getDeliveryData() == null) {
            throw new DeliveryDataRequiredException();//todo zmiana w klasie wyjątku i w advice
        }
        customerId.ifPresent(id -> {
            Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
            Basket newEmptyBasket = new Basket();
            basketRepository.save(newEmptyBasket);
            customer.setBasket(newEmptyBasket);
            customer.addOrderToList(order);
            customerRepository.save(customer);
        });

        orderRepository.save(order);

    }

    public void recalculateBasketTotalPRice(Long basketId) {


    }
}
