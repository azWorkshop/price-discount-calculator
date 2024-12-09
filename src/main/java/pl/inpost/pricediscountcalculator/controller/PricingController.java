package pl.inpost.pricediscountcalculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Calculate given product price using discount policy based on product quantity")
    @GetMapping("/{id}/price")
    public ResponseEntity<BigDecimal> calculatePrice(
            @Parameter(description = "id of product to be searched") @PathVariable @NotNull(message = "Product ID is required") UUID id,
            @Parameter(description = "quantity of product to calculate discount") @RequestParam @NotNull @Positive(message = "Quantity must be greater than zero") int quantity) {
        return ResponseEntity.ok(pricingService.calculatePrice(id, quantity));
    }
}
