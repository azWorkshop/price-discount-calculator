package pl.inpost.pricediscountcalculator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.inpost.pricediscountcalculator.model.DiscountPolicy;

import java.util.List;
import java.util.UUID;

@Repository
public interface DiscountPolicyRepository extends JpaRepository<DiscountPolicy, Long> {
    List<DiscountPolicy> findByProductId(UUID productId);
}
