package com.orka.restapishop.service;


import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.dto.DiscountCodeDto;
import com.orka.restapishop.excepiton.*;
import com.orka.restapishop.model.*;
import com.orka.restapishop.repository.*;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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


    public void updateBasketProducts(BasketDto basketDto, Long basketId) {

        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
        updateProductAmount(basketDto, basket);
        basketRepository.save(basket);
    }

    private void updateProductAmount(BasketDto basketDto, Basket basket) {

        if (basketDto.getProducts() == null) {
            return;
        }
        basketDto.getProducts().entrySet().stream().forEach(entry -> {
            Long productId = entry.getKey();
            int requestedAmount = entry.getValue();
            Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
            basket.updateProduct(product, requestedAmount);
        });
    }

    public void updateBasketDiscountCode(BasketDto basketDto, Long basketId) {

        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
        updateDiscountCode(basketDto, basket);
        basketRepository.save(basket);
    }

    private void updateDiscountCode(BasketDto basketDto, Basket basket) {
        String discountCode = basketDto.getDiscountCode();

        if (discountCode == null) {
            return;
        } else if (basket.getDiscountCode() != null && discountCode.equals(basket.getDiscountCode().getName())){
            return;
        }
            applyDiscount(basketDto, basket);
    }

    private void applyDiscount(BasketDto basketDto, Basket basket) {
        DiscountCode discount = discountCodeRepository.findByName(basketDto.getDiscountCode().toLowerCase()).orElseThrow(); //todo zrobić exception i advice
        basket.setDiscountCode(discount);
    }


    public void deleteProductFromBasket(Long basketId, Long productId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
        basket.deleteProduct(productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId)));
        basketRepository.save(basket);
    }

    public DeliveryDataDto setDeliveryData(Long basketId, DeliveryDataDto deliveryDataDto) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));//wyekstraktować do metod
        DeliveryData deliveryData = deliveryDataService.createAndSaveDeliveryData(deliveryDataDto);
        basket.setDeliveryData(deliveryData);
        basketRepository.save(basket);
        return deliveryData.mapToDto();
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

    public BasketDto recalculateBasketTotalPRice(Long basketId) {
        Basket basket = basketRepository.findById(basketId).orElseThrow(() -> new BasketNotFoundException(basketId));
        basket.calculateTotalPrice();
        basketRepository.save(basket);
        return basket.mapToDto();
    }
}
