package pl.inpost.pricediscountcalculator.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class AmountBasedDiscountRequest {

    @Positive(message = "Threshold must be greater than zero")
    private int threshold;

    @NotNull(message = "Discount amount is required")
    @Positive(message = "Discount amount must be greater than zero")
    private BigDecimal discount;

    @NotNull(message = "Product id is required")
    private UUID product;
}
