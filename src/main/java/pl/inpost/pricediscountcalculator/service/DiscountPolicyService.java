package pl.inpost.pricediscountcalculator.service;

import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import pl.inpost.pricediscountcalculator.dto.AmountBasedDiscountRequest;
import pl.inpost.pricediscountcalculator.dto.PercentageBasedDiscountRequest;
import pl.inpost.pricediscountcalculator.model.AmountDiscountPolicy;
import pl.inpost.pricediscountcalculator.model.DiscountPolicy;
import pl.inpost.pricediscountcalculator.model.PercentageDiscountPolicy;
import pl.inpost.pricediscountcalculator.repository.DiscountPolicyRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DiscountPolicyService {

    private final DiscountPolicyRepository discountPolicyRepository;

    public DiscountPolicyService(DiscountPolicyRepository discountPolicyRepository) {
        this.discountPolicyRepository = discountPolicyRepository;
    }

    List<DiscountPolicy> getDiscountPoliciesByProductId(UUID productId) {
        return discountPolicyRepository.findByProductId(productId);
    }

    public DiscountPolicy addAmountBasedDiscount(@Valid AmountBasedDiscountRequest discountRequest) {
        AmountDiscountPolicy newPolicy = mapToDiscountPolicy(discountRequest);

        List<DiscountPolicy> existingPolicies = discountPolicyRepository.findByProductId(newPolicy.getProductId());
        Optional<DiscountPolicy> existingPolicy = existingPolicies.stream()
                .filter(policy -> policy instanceof AmountDiscountPolicy discountPolicy && discountPolicy.getThreshold() == newPolicy.getThreshold())
                .findFirst();

        if (existingPolicy.isPresent()) {
            AmountDiscountPolicy policyToUpdate = (AmountDiscountPolicy) existingPolicy.get();
            if (policyToUpdate.getAmount().compareTo(newPolicy.getAmount()) == 0) {
                return policyToUpdate;
            }
            policyToUpdate.setAmount(newPolicy.getAmount());
            return discountPolicyRepository.save(policyToUpdate);
        }

        return discountPolicyRepository.save(newPolicy);
    }

    public DiscountPolicy addPercentageBasedDiscount(@Valid PercentageBasedDiscountRequest discountRequest) {
        PercentageDiscountPolicy newPolicy = mapToDiscountPolicy(discountRequest);

        List<DiscountPolicy> existingPolicies = discountPolicyRepository.findByProductId(newPolicy.getProductId());
        Optional<DiscountPolicy> existingPolicy = existingPolicies.stream()
                .filter(policy -> policy instanceof PercentageDiscountPolicy discountPolicy && discountPolicy.getThreshold() == newPolicy.getThreshold())
                .findFirst();

        if (existingPolicy.isPresent()) {
            PercentageDiscountPolicy policyToUpdate = (PercentageDiscountPolicy) existingPolicy.get();
            if (policyToUpdate.getPercentage() == newPolicy.getPercentage()) {
                return policyToUpdate;
            }
            policyToUpdate.setPercentage(newPolicy.getPercentage());
            return discountPolicyRepository.save(policyToUpdate);
        }

        return discountPolicyRepository.save(newPolicy);
    }

    private AmountDiscountPolicy mapToDiscountPolicy(AmountBasedDiscountRequest request) {
        return AmountDiscountPolicy.builder()
                .productId(request.getProduct())
                .threshold(request.getThreshold())
                .amount(request.getDiscount())
                .build();
    }

    private PercentageDiscountPolicy mapToDiscountPolicy(PercentageBasedDiscountRequest request) {
        return PercentageDiscountPolicy.builder()
                .productId(request.getProduct())
                .threshold(request.getThreshold())
                .percentage(request.getPercentage())
                .build();
    }
}
