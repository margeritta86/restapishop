package com.orka.restapishop.service;
import com.orka.restapishop.dto.BasketDto;
import com.orka.restapishop.dto.DeliveryDataDto;
import com.orka.restapishop.excepiton.BasketNotFoundException;
import com.orka.restapishop.excepiton.CustomerNotFoundException;
import com.orka.restapishop.excepiton.ProductNotFoundException;
import com.orka.restapishop.model.*;
import com.orka.restapishop.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;



import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@RunWith(SpringRunner.class)
class BasketServiceTest {

    private ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    private DiscountCodeRepository discountCodeRepository = Mockito.mock(DiscountCodeRepository.class);
    private BasketRepository basketRepository = Mockito.mock(BasketRepository.class);
    private CustomerRepository customerRepository = Mockito.mock(CustomerRepository.class);
    private OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    private DeliveryDataRepository deliveryDataRepository = Mockito.mock(DeliveryDataRepository.class);
    private AddressRepository addressRepository = Mockito.mock(AddressRepository.class);
    @InjectMocks
    private DeliveryDataService deliveryDataService = new DeliveryDataService(deliveryDataRepository,addressRepository);
    @InjectMocks
    private BasketService basketService = new BasketService(basketRepository, customerRepository, productRepository, orderRepository, discountCodeRepository, deliveryDataService);


    private Product prod1 = new Product("Ham", 5.07, "imgUrl1", 10);
    private Product prod2 = new Product("Mushrooms", 8.08, "imgUrl2", 10);
    private Product prod3 = new Product("Pineapple", 4.05, "imgUrl3", 10);

    private Basket basket1 = new Basket();
    private Basket basket2 = new Basket();
    private Basket basket3 = new Basket();


    @BeforeAll
    void setupProductsAndBaskets() {

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

    }

    @Test
    void shouldSaveProductToBasket() {
        //given
        Mockito.when(basketRepository.findById(basket1.getId())).thenReturn(Optional.of(basket1));
        Mockito.when(productRepository.findById(prod1.getId())).thenReturn(Optional.of(prod1));
        //when
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
    void shouldNotReturnNullGettingBasketById() {
        //given
        //when
        Mockito.when(basketRepository.findById(basket1.getId())).thenReturn(Optional.of(basket1));
        BasketDto basketDto = basket1.mapToDto();

        //then
        assertNotNull(basket1);
        assertNotNull(basketDto);
    }

    @Test
    void shouldUpdateAmountOfProductsBasket() {

        //given
        Mockito.when(basketRepository.findById(basket1.getId())).thenReturn(Optional.of(basket1));
        Mockito.when(productRepository.findById(prod1.getId())).thenReturn(Optional.of(prod1));
        Map<Product, Integer> productsToAdd = new HashMap<>();
        Map<Product, Integer> productsInBasket = new HashMap<>();
        productsInBasket.put(prod1, 1);
        basket1.setProducts(productsInBasket);
        productsToAdd.put(prod1,5);
        basket1.setProducts(productsToAdd);
        BasketDto basketDto = basket1.mapToDto();

        //when
        basketService.updateBasketProducts(basketDto, basket1.getId());

        //then
        assertEquals(5, basketDto.getProducts().get(prod1.getId()));

    }

    @Test
    void shouldAddDiscountCodeToBasket() {
        //given
        Mockito.when(basketRepository.findById(basket1.getId())).thenReturn(Optional.of(basket1));
        DiscountCode discountCode = new DiscountCode("wiosna", 20, LocalDate.now().plusMonths(3));
        Mockito.when(discountCodeRepository.findByName("wiosna")).thenReturn(Optional.of(discountCode));
        BasketDto basketDto = basket1.mapToDto();
        basketDto.setDiscountCode("wiosna");
        //when
        basketService.updateBasketDiscountCode(basketDto, basket1.getId());
        //then
        assertEquals(discountCode, basket1.getDiscountCode());
    }

    @Test
    void shouldDeleteProductFromBasket() {
        //given
        Mockito.when(basketRepository.findById(basket1.getId())).thenReturn(Optional.of(basket1));
        Mockito.when(productRepository.findById(prod1.getId())).thenReturn(Optional.of(prod1));
        Mockito.when(productRepository.findById(prod2.getId())).thenReturn(Optional.of(prod2));
        Map<Product, Integer> products = new HashMap<>();
        products.put(prod1, 2);
        products.put(prod2, 5);
        basket1.setProducts(products);
        //when
        basketService.deleteProductFromBasket(basket1.getId(), prod2.getId());

        //then
        assertFalse(basket2.getProducts().containsKey(prod2));
    }

    @Test
    void shouldAddDeliveryDataToBasket() {

        //given
        Mockito.when(basketRepository.findById(basket1.getId())).thenReturn(Optional.of(basket1));
        Address address = new Address("Wrocław","Piastowska","27","44","55-010","Polska");
        DeliveryData deliveryData = new DeliveryData(address,"Małgosia","Kochanowska","malgosia@gmail.com");
        deliveryDataRepository.save(deliveryData);
        DeliveryDataDto deliveryDataDto = deliveryData.mapToDto();

        //when
        basketService.setDeliveryData(basket1.getId(),deliveryDataDto);

        //then
        Assertions.assertTrue(basket1.getDeliveryData().equals(deliveryData));
    }



    @Test
    void shouldThrowBasketNotFoundException() {
        Assertions.assertThrows(BasketNotFoundException.class, () -> basketService.findBasketById(44L));
    }

    @Test
    void shouldThrowProductNotFoundException() {

        Assertions.assertThrows(ProductNotFoundException.class, () -> basketService.findProductById(44L));
    }

    @Test
    void shouldThrowCustomerNotFoundException() {

        Assertions.assertThrows(CustomerNotFoundException.class, () -> basketService.findCustomerById(44L));
    }


}