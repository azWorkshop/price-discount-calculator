package pl.inpost.pricediscountcalculator.service;

import org.springframework.stereotype.Service;
import pl.inpost.pricediscountcalculator.exception.ProductNotFoundException;
import pl.inpost.pricediscountcalculator.model.DiscountPolicy;
import pl.inpost.pricediscountcalculator.model.product.Product;
import pl.inpost.pricediscountcalculator.model.product.ProductSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PricingService {

    private final ProductSource productSource;
    private final DiscountPolicyService discountPolicyService;

    public PricingService(ProductSource productSource, DiscountPolicyService discountPolicyService) {
        this.productSource = productSource;
        this.discountPolicyService = discountPolicyService;
    }

    public BigDecimal calculatePrice(UUID productId, int quantity) {
        Optional<Product> product = getProductById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException();
        }

        BigDecimal price = product.get().getPrice();
        BigDecimal basePrice = price.multiply(BigDecimal.valueOf(quantity));

        List<DiscountPolicy> discountPolicies = discountPolicyService.getDiscountPoliciesByProductId(productId);
        for (DiscountPolicy discountPolicy : discountPolicies) {
            basePrice = discountPolicy.applyDiscount(basePrice, quantity);
        }

        return basePrice;
    }

    private Optional<Product> getProductById(UUID id) {
        return productSource.getProductById(id);
    }
}
