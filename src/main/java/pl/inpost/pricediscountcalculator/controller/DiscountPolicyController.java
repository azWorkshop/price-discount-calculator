package pl.inpost.pricediscountcalculator.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.inpost.pricediscountcalculator.dto.AmountBasedDiscountRequest;
import pl.inpost.pricediscountcalculator.dto.PercentageBasedDiscountRequest;
import pl.inpost.pricediscountcalculator.model.DiscountPolicy;
import pl.inpost.pricediscountcalculator.service.DiscountPolicyService;

@RestController
@RequestMapping("/discounts")
public class DiscountPolicyController {

    private final DiscountPolicyService discountPolicyService;

    public DiscountPolicyController(DiscountPolicyService discountPolicyService) {
        this.discountPolicyService = discountPolicyService;
    }

    @PostMapping("/amount")
    public ResponseEntity<DiscountPolicy> addAmountBasedDiscount(@RequestBody @Valid AmountBasedDiscountRequest request) {
        return ResponseEntity.ok(discountPolicyService.addAmountBasedDiscount(request));
    }

    @PostMapping("/percentage")
    public ResponseEntity<DiscountPolicy> addPercentageBasedDiscount(@RequestBody @Valid PercentageBasedDiscountRequest request) {
        return ResponseEntity.ok(discountPolicyService.addPercentageBasedDiscount(request));
    }
}
