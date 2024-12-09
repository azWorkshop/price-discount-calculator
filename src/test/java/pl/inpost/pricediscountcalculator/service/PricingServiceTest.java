package pl.inpost.pricediscountcalculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.inpost.pricediscountcalculator.exception.ProductNotFoundException;
import pl.inpost.pricediscountcalculator.model.DiscountPolicy;
import pl.inpost.pricediscountcalculator.model.product.Product;
import pl.inpost.pricediscountcalculator.model.product.ProductSource;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PricingServiceTest {
    @Mock
    ProductSource productSource;
    @Mock
    DiscountPolicyService discountPolicyService;
    @Mock
    private Product product;
    @Mock
    private DiscountPolicy discountPolicy;
    @InjectMocks
    PricingService pricingService;

    private UUID productId;
    private int quantity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productId = UUID.randomUUID();
        quantity = 5;
    }

    @Test
    void testCalculatePrice_ProductFound_NoDiscount() {
        when(productSource.getProductById(productId)).thenReturn(Optional.of(product));
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(100));

        BigDecimal price = pricingService.calculatePrice(productId, quantity);

        assertEquals(BigDecimal.valueOf(500), price);
    }

    @Test
    void testCalculatePrice_ProductFound_WithDiscount() {
        when(productSource.getProductById(productId)).thenReturn(Optional.of(product));
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(100));
        when(discountPolicyService.getDiscountPoliciesByProductId(productId)).thenReturn(Collections.singletonList(discountPolicy));
        when(discountPolicy.applyDiscount(any(BigDecimal.class), anyInt())).thenReturn(BigDecimal.valueOf(450));

        BigDecimal price = pricingService.calculatePrice(productId, quantity);

        assertEquals(BigDecimal.valueOf(450), price);
    }

    @Test
    void testCalculatePrice_ProductNotFound() {
        when(productSource.getProductById(productId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            pricingService.calculatePrice(productId, quantity);
        });

        assertNotNull(exception);
    }

    @Test
    void testCalculatePrice_ZeroQuantity() {
        when(productSource.getProductById(productId)).thenReturn(Optional.of(product));
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(100));

        BigDecimal price = pricingService.calculatePrice(productId, 0);

        assertEquals(BigDecimal.ZERO, price);
    }
}