package pl.inpost.pricediscountcalculator.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.inpost.pricediscountcalculator.dto.AmountBasedDiscountRequest;
import pl.inpost.pricediscountcalculator.model.AmountDiscountPolicy;
import pl.inpost.pricediscountcalculator.model.DiscountPolicy;
import pl.inpost.pricediscountcalculator.repository.DiscountPolicyRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DiscountPolicyServiceTest {

    @Mock
    private DiscountPolicyRepository discountPolicyRepository;

    @InjectMocks
    private DiscountPolicyService discountPolicyService;

    private UUID productId = UUID.randomUUID();

    private AmountBasedDiscountRequest validRequest;
    private AmountDiscountPolicy newPolicy;
    private AmountDiscountPolicy existingPolicy;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        validRequest = createAmountBasedDiscountRequest(productId, new BigDecimal("10.00"));
        newPolicy = createAmountDiscountPolicy(productId, new BigDecimal("10.00"));
        existingPolicy = createAmountDiscountPolicy(productId, new BigDecimal("5.00"));
    }

    @Test
    void testAddAmountBasedDiscount_NewPolicy() {
        when(discountPolicyRepository.findByProductId(productId)).thenReturn(Arrays.asList());
        when(discountPolicyRepository.save(any())).thenReturn(newPolicy);

        DiscountPolicy result = discountPolicyService.addAmountBasedDiscount(validRequest);

        assertNotNull(result);
        assertEquals(validRequest.getDiscount(), ((AmountDiscountPolicy) result).getAmount());
        verify(discountPolicyRepository).save(any(AmountDiscountPolicy.class));
    }

    @Test
    public void testAddAmountBasedDiscount_UpdateExistingPolicy_SameAmount() {
        existingPolicy.setAmount(validRequest.getDiscount());
        when(discountPolicyRepository.findByProductId(productId)).thenReturn(Arrays.asList(existingPolicy));

        DiscountPolicy result = discountPolicyService.addAmountBasedDiscount(validRequest);

        assertEquals(existingPolicy, result);
        verify(discountPolicyRepository, never()).save(any());
    }

    @Test
    public void testAddAmountBasedDiscount_UpdateExistingPolicy_DifferentAmount() {
        existingPolicy.setAmount(new BigDecimal("5.00"));
        when(discountPolicyRepository.findByProductId(productId)).thenReturn(Arrays.asList(existingPolicy));
        when(discountPolicyRepository.save(any())).thenReturn(newPolicy);

        DiscountPolicy result = discountPolicyService.addAmountBasedDiscount(validRequest);

        assertEquals(validRequest.getDiscount(), ((AmountDiscountPolicy) result).getAmount());
        assertEquals(existingPolicy.getProductId(), ((AmountDiscountPolicy) result).getProductId());
        verify(discountPolicyRepository).save(existingPolicy);
    }

    @Test
    public void testAddAmountBasedDiscount_NoExistingPolicies() {
        when(discountPolicyRepository.findByProductId(productId)).thenReturn(Arrays.asList());
        when(discountPolicyRepository.save(any())).thenReturn(newPolicy);

        DiscountPolicy result = discountPolicyService.addAmountBasedDiscount(validRequest);

        assertNotNull(result);
        assertEquals(validRequest.getDiscount(), ((AmountDiscountPolicy) result).getAmount());
        verify(discountPolicyRepository).save(any(AmountDiscountPolicy.class));
    }

    @Test
    public void testAddAmountBasedDiscount_ExistingPolicyWithDifferentThreshold() {
        existingPolicy.setThreshold(200);
        when(discountPolicyRepository.findByProductId(productId)).thenReturn(Arrays.asList(existingPolicy));
        when(discountPolicyRepository.save(any())).thenReturn(newPolicy);

        DiscountPolicy result = discountPolicyService.addAmountBasedDiscount(validRequest);

        assertNotNull(result);
        assertEquals(validRequest.getDiscount(), ((AmountDiscountPolicy) result).getAmount());
        verify(discountPolicyRepository).save(any(AmountDiscountPolicy.class));
    }

    @Test
    void testGetDiscountPoliciesByProductId_ValidProductId() {
        UUID productId = UUID.randomUUID();
        List<DiscountPolicy> expectedPolicies = Collections.singletonList(createAmountDiscountPolicy(productId, new BigDecimal("20.00")));

        when(discountPolicyRepository.findByProductId(productId)).thenReturn(expectedPolicies);

        List<DiscountPolicy> actualPolicies = discountPolicyService.getDiscountPoliciesByProductId(productId);

        assertEquals(expectedPolicies, actualPolicies);
    }

    @Test
    void testGetDiscountPoliciesByProductId_NoDiscountPolicies() {
        UUID productId = UUID.randomUUID();

        when(discountPolicyRepository.findByProductId(productId)).thenReturn(Collections.emptyList());

        List<DiscountPolicy> actualPolicies = discountPolicyService.getDiscountPoliciesByProductId(productId);

        assertTrue(actualPolicies.isEmpty());
    }

    @Test
    void testGetDiscountPoliciesByProductId_InvalidProductId() {
        UUID invalidProductId = UUID.randomUUID();

        when(discountPolicyRepository.findByProductId(invalidProductId)).thenReturn(null);

        List<DiscountPolicy> actualPolicies = discountPolicyService.getDiscountPoliciesByProductId(invalidProductId);

        assertNull(actualPolicies);
    }

    private AmountBasedDiscountRequest createAmountBasedDiscountRequest(UUID productId, BigDecimal discountAmount) {
        AmountBasedDiscountRequest request = new AmountBasedDiscountRequest();
        request.setProduct(productId);
        request.setThreshold(100);
        request.setDiscount(discountAmount);

        return request;
    }

    private AmountDiscountPolicy createAmountDiscountPolicy(UUID productId, BigDecimal discountAmount) {
        return AmountDiscountPolicy.builder()
                .productId(productId)
                .threshold(100)
                .amount(discountAmount)
                .build();
    }
}