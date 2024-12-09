package pl.inpost.pricediscountcalculator.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Schema(description = "Discount policy request data for creating a discount policy for given product based on quantity and discount percentage")
@Data
public class PercentageBasedDiscountRequest {
    @Schema(description = "Quantity of the product to apply discount", example = "5")
    @Positive(message = "Threshold must be greater than zero")
    private int threshold;

    @Schema(description = "Percentage discount applied if product quantity match", example = "5%")
    @NotNull(message = "Discount percentage is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount percentage must be greater than 0")
    @DecimalMax(value = "100.0", message = "Discount percentage must be less than or equal to 100")
    private double percentage;

    @Schema(description = "Product UUID for discount policy", example = "d35e8da1-601e-4be4-8e24-66968f7b95e9")
    @NotNull(message = "Product id is required")
    private UUID product;
}
