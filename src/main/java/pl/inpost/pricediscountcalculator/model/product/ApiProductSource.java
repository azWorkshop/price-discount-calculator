package pl.inpost.pricediscountcalculator.model.product;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Component
public class ApiProductSource implements ProductSource {

    @Value("${product.service.url}")
    private String productApiUrl;
    private final RestTemplate restTemplate;

    public ApiProductSource(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        try {
            Product product = restTemplate.getForObject(productApiUrl + id, Product.class);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
