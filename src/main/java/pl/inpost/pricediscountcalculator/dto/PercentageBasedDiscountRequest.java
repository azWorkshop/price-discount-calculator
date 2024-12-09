package pl.inpost.pricediscountcalculator.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.UUID;

@Data
public class PercentageBasedDiscountRequest {

    @Positive(message = "Threshold must be greater than zero")
    private int threshold;

    @NotNull(message = "Discount percentage is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount percentage must be greater than 0")
    @DecimalMax(value = "100.0", message = "Discount percentage must be less than or equal to 100")
    private double percentage;

    @NotNull(message = "Product id is required")
    private UUID product;
}
