package pl.inpost.pricediscountcalculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Schema(description = "Discount policy request data for creating a discount policy for given product based on quantity and discount amount")
@Data
public class AmountBasedDiscountRequest {
    @Schema(description = "Quantity of the product to apply discount", example = "5")
    @Positive(message = "Threshold must be greater than zero")
    private int threshold;

    @Schema(description = "Discount applied if product quantity match", example = "5USD")
    @NotNull(message = "Discount amount is required")
    @Positive(message = "Discount amount must be greater than zero")
    private BigDecimal discount;

    @Schema(description = "Product UUID for discount policy", example = "d35e8da1-601e-4be4-8e24-66968f7b95e9")
    @NotNull(message = "Product id is required")
    private UUID product;
}
