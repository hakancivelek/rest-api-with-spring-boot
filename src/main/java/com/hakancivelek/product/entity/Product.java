package com.hakancivelek.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

//Test for git
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "price", nullable = false, precision = 19, scale = 2)
    private BigDecimal price;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "sku", length = 100, unique = true)
    private String sku;

    @Column(name = "barcode", length = 100, unique = true)
    private String barcode;

    public Product() {
    }

    public Product(
            Long id,
            String name,
            BigDecimal price,
            String description,
            String sku,
            String barcode
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.sku = sku;
        this.barcode = barcode;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Product setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSku() {
        return sku;
    }

    public Product setSku(String sku) {
        this.sku = sku;
        return this;
    }

    public String getBarcode() {
        return barcode;
    }

    public Product setBarcode(String barcode) {
        this.barcode = barcode;
        return this;
    }
}


