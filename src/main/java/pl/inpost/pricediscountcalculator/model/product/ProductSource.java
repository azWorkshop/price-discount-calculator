package pl.inpost.pricediscountcalculator.model.product;

import java.util.Optional;
import java.util.UUID;

public interface ProductSource {
    Optional<Product> getProductById(UUID id);
}
