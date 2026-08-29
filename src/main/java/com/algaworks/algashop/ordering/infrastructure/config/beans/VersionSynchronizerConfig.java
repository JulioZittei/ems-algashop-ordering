package com.algaworks.algashop.ordering.infrastructure.config.beans;

import com.algaworks.algashop.ordering.domain.model.customer.CustomerVersionSynchronizer;
import com.algaworks.algashop.ordering.domain.model.order.OrderVersionSynchronizer;
import com.algaworks.algashop.ordering.domain.model.shoppingcart.ShoppingCartVersionSynchronizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VersionSynchronizerConfig {

    @Bean
    public OrderVersionSynchronizer orderVersionSynchronizer() {
        return new OrderVersionSynchronizer();
    }

    @Bean
    public CustomerVersionSynchronizer customerVersionSynchronizer() {
        return new CustomerVersionSynchronizer();
    }

    @Bean
    public ShoppingCartVersionSynchronizer shoppingCartVersionSynchronizer() {return new ShoppingCartVersionSynchronizer();}
}
