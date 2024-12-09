package pl.inpost.pricediscountcalculator.model.product;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class InMemoryProductSource implements ProductSource {
    private final Map<UUID, Product> products = new HashMap<>();

    public InMemoryProductSource() {
        Product product1 = Product.builder()
                .id(UUID.fromString("d35e8da1-601e-4be4-8e24-66968f7b95e9"))
                .name("Product1")
                .price(BigDecimal.valueOf(10.99))
                .build();
        products.put(product1.getId(), product1);

        Product product2 = Product.builder()
                .id(UUID.fromString("ffe172d4-34c2-4b6c-b938-a25429d599a3"))
                .name("Product2")
                .price(BigDecimal.valueOf(4.58))
                .build();
        products.put(product2.getId(), product2);
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }
}
