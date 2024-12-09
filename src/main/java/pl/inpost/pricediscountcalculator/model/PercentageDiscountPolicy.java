package pl.inpost.pricediscountcalculator.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("PERCENTAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Setter
public class PercentageDiscountPolicy extends DiscountPolicy {
    private int threshold;
    private double percentage;

    @Override
    public BigDecimal applyDiscount(BigDecimal price, int quantity) {
        if (quantity >= threshold) {
            BigDecimal priceAfterDiscount = price.multiply(BigDecimal.valueOf(1 - percentage / 100));
            return (priceAfterDiscount.signum() == -1) ? price : priceAfterDiscount;
        }
        return price;
    }
}
