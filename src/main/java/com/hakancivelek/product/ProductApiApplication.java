package com.hakancivelek.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.hakancivelek.product", "com.hakancivelek.auth"})
@EnableJpaRepositories(basePackages = {"com.hakancivelek.product.repository", "com.hakancivelek.auth.repository"})
@EntityScan(basePackages = {"com.hakancivelek.product.entity", "com.hakancivelek.auth.entity"})
public class ProductApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApiApplication.class, args);
    }

}
