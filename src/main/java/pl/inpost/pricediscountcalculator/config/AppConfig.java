package pl.inpost.pricediscountcalculator.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.inpost.pricediscountcalculator.model.product.ApiProductSource;

@Configuration
@ConditionalOnBean(ApiProductSource.class)
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
