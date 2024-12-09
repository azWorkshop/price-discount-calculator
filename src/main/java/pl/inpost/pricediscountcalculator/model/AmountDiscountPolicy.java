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
@DiscriminatorValue("AMOUNT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@Getter
@Setter
public class AmountDiscountPolicy extends DiscountPolicy {
    private int threshold;
    private BigDecimal amount;

    @Override
    public BigDecimal applyDiscount(BigDecimal price, int quantity) {
        if (quantity >= threshold) {
            BigDecimal priceAfterDiscount = price.subtract(amount);
            return (priceAfterDiscount.signum() == -1) ? price : priceAfterDiscount;
        }
        return price;
    }
}
