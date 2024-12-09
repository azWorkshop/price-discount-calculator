package pl.inpost.pricediscountcalculator.model.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class Product {
    private UUID id;
    private String name;
    private BigDecimal price;
}
