package pl.inpost.pricediscountcalculator.controller;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.inpost.pricediscountcalculator.service.PricingService;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/products")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/{id}/price")
    public ResponseEntity<BigDecimal> calculatePrice(
            @PathVariable @NotNull(message = "Product ID is required") UUID id,
            @RequestParam @NotNull @Positive(message = "Quantity must be greater than zero") int quantity) {
        return ResponseEntity.ok(pricingService.calculatePrice(id, quantity));
    }
}
