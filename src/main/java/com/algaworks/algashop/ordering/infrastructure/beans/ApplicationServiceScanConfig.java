package com.algaworks.algashop.ordering.infrastructure.beans;

import com.algaworks.algashop.ordering.application.ApplicationService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.algaworks.algashop.ordering.application",
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = ApplicationService.class
        )
)
public class ApplicationServiceScanConfig {
}