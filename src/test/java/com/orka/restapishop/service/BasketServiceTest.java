package com.orka.restapishop.service;

import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.excepiton.BasketNotFoundException;
import com.orka.restapishop.excepiton.ProductNotFoundException;
import com.orka.restapishop.model.Basket;
import com.orka.restapishop.model.DeliveryData;
import com.orka.restapishop.model.DiscountCode;
import com.orka.restapishop.model.Product;
import com.orka.restapishop.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
@SpringBootTest
class BasketServiceTest {


    //    @Autowired
    private ProductRepository productRepository;

   // @Autowired
    private BasketRepository basketRepository;

    //@Autowired
    private BasketService basketService;

    //@Autowired
    private DiscountCodeRepository discountCodeRepository;
    private CustomerRepository customerRepository;
    private OrderRepository orderRepository;
    private DeliveryDataService deliveryDataService;

    private Product prod1 = new Product("Ham", new BigDecimal("5.07"), "imgUrl1", 10);
    private Product prod2 = new Product("Mushrooms", new BigDecimal("8.08"), "imgUrl2", 10);
    private Product prod3 = new Product("Pineapple", new BigDecimal("4.05"), "imgUrl3", 10);

    private Basket basket1 = new Basket();
    private Basket basket2 = new Basket();
    private Basket basket3 = new Basket();


    @BeforeAll
    void setupProductsAndBaskets() {

        productRepository = Mockito.mock(ProductRepository.class);
        discountCodeRepository = Mockito.mock(DiscountCodeRepository.class);
        basketRepository = Mockito.mock(BasketRepository.class);
        customerRepository = Mockito.mock(CustomerRepository.class);
        orderRepository = Mockito.mock(OrderRepository.class);
        basketRepository = Mockito.mock(BasketRepository.class);
        basketService = new BasketService(basketRepository,customerRepository,productRepository,orderRepository,discountCodeRepository,deliveryDataService);

        prod1.setId(1);
        prod2.setId(2);
        prod3.setId(3);

        basket1.setId(1);
        basket2.setId(2);
        basket3.setId(3);

        basketRepository.save(basket1);
        basketRepository.save(basket2);
        basketRepository.save(basket3);

        productRepository.save(prod1);
        productRepository.save(prod2);
        productRepository.save(prod3);


//
    }

    @Test
    void shouldSaveProductToBasket() {
        //given
        basketRepository.save(basket1);
        productRepository.save(prod1);
        basketService.saveProductToBasket(basket1.getId(), prod1.getId(), 1);

        //then
        assertTrue(basket1.getProducts().containsKey(prod1));
    }


    @Test
    void shouldApplyDiscountCodeToBasket() {
        //given
        Basket basket = new Basket();
        DiscountCode discountCode = new DiscountCode("summer", 20, LocalDate.now().plusMonths(3));
        discountCodeRepository.save(discountCode);
        BasketDto basketDto = basket.mapToDto();
        basketDto.setDiscountCode(discountCode.getName());
        Mockito.when(discountCodeRepository.findByName(basketDto.getDiscountCode().toLowerCase())).thenReturn(Optional.of(discountCode));
        //when
        basketService.applyDiscount(basketDto, basket);
        //then
        Assertions.assertEquals(discountCode, basket.getDiscountCode());
        Mockito.verify(discountCodeRepository).save(discountCode);
    }

    @Test
    void fillDataBase() {
    }

    @Test
    void shouldGetBasket() {
        //given
        Basket basket = basketRepository.findById(1L).get();
        //when
        BasketDto basketDto = basketService.getBasketDtoById(1L);
        //then
        assertNotNull(basketDto);
        assertTrue(basketRepository.existsById(1L));
    }

    @Test
    void shouldUpdateAmountOfProductsBasket() {
        //given
        Basket basket = basketRepository.findById(1L).get();
        Product product = productRepository.findById(1L).get();
        Map<Long, Integer> productsToAdd = new HashMap<>();
        Map<Product, Integer> productsInBasket = new HashMap<>();

        productsInBasket.put(product, 1);
        basket.setProducts(productsInBasket);
        basketRepository.save(basket);

        BasketDto basketDto = basket.mapToDto();
        productsToAdd.put(product.getId(), 5);
        basketDto.setProducts(productsToAdd);
        //when
        basketService.updateBasketProducts(basketDto, 1L);

        //then
        assertEquals(5, basketDto.getProducts().get(product.getId()));

    }

    @Test
    void shouldAddDiscountCodeToBasket() {
        //given
        Basket basket = basketRepository.findById(1L).get();
        DiscountCode discountCode = new DiscountCode("wiosna", 20, LocalDate.now().plusMonths(3));
        discountCodeRepository.save(discountCode);
        BasketDto basketDto = basket.mapToDto();
        basketDto.setDiscountCode(discountCode.getName());
        //when
        basketService.updateBasketDiscountCode(basketDto, basket.getId());
        Basket basket2 = basketRepository.findById(1L).get();//bez tego nie działał test???
        //then
        assertEquals(discountCode, basket2.getDiscountCode());

    }

    @Test
    void shouldDeleteProductFromBasket() {
        //given
        Basket basket = basketRepository.findById(1L).get();
        Product product = productRepository.findById(1L).get();
        Product product2 = productRepository.findById(2L).get();
        Map<Product, Integer> products = new HashMap<>();
        products.put(product, 2);
        products.put(product2, 5);
        basket.setProducts(products);
        basketRepository.save(basket);
        //when
        basketService.deleteProductFromBasket(1L, product2.getId());
        Basket basket2 = basketRepository.findById(1L).get();//bez tego nie działał test???

        //then
        assertFalse(basket2.getProducts().containsKey(product2));
    }

    @Test
    void shouldAddDeliveryDataToBasket() {
        //given
        Basket basket5 = new Basket();
        //DeliveryDataDto deliveryDataDto = new DeliveryDataDto();
        /*new DeliveryDataDto("Wrocław","Grunwaldzka","15","20","70-233","Polska","Małgosia","Kowalska","malgosia@gmail.com");*/


    }

    @Test
    void placeOrder() {

    }

    @Test
    void recalculateBasketTotalPrice() {
    }

    @Test
    void shouldFindTheBasketOrThrowException(Long id) {
        basketRepository.findById(id).orElseThrow(() -> new BasketNotFoundException(id));
    }

    @Test
    void shouldFindTheProductOrThrowException(Long id) {
        productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Test
    void shouldThrowBasketNotFoundException() {
        Assertions.assertThrows(BasketNotFoundException.class, () -> shouldFindTheBasketOrThrowException(44L));
    }

    @Test
    void shouldThrowProductNotFoundException() {

        Assertions.assertThrows(ProductNotFoundException.class, () -> basketService.findProductById(44L));
    }


}