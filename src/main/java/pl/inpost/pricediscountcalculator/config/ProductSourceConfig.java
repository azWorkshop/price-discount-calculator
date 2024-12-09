package pl.inpost.pricediscountcalculator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import pl.inpost.pricediscountcalculator.model.product.ApiProductSource;
import pl.inpost.pricediscountcalculator.model.product.InMemoryProductSource;
import pl.inpost.pricediscountcalculator.model.product.ProductSource;

@Configuration
public class ProductSourceConfig {
    private final Environment env;

    public ProductSourceConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public ProductSource productSource(ApiProductSource apiProductSource, InMemoryProductSource memoryProductSource) {
        String sourceType = env.getProperty("product.source.type", "memory");
        return switch (sourceType) {
            case "memory" -> memoryProductSource;
            case "api" -> apiProductSource;
            default -> memoryProductSource;
        };
    }
}
