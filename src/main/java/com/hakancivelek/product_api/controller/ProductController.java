package com.hakancivelek.product_api.controller;

import com.hakancivelek.product_api.dto.ProductRequest;
import com.hakancivelek.product_api.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/products")
public class ProductController {
    private final Map<String, ProductResponse> products = new HashMap<>();

    @GetMapping("/{id}")
    public EntityModel<ProductResponse> get(@PathVariable String id) {
        ProductResponse product = products.get(id);
        if (product == null) {
            throw new NoSuchElementException("Product not found");
        }
        return toModel(product);
    }

    @GetMapping
    public CollectionModel<EntityModel<ProductResponse>> list() {
        List<EntityModel<ProductResponse>> productsResponse = products.values().stream()
                .map(this::toModel)
                .toList();
        return CollectionModel.of(productsResponse,
                linkTo(methodOn(ProductController.class).list()).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<ProductResponse>> create(@Valid @RequestBody ProductRequest productRequest) {
        String id = UUID.randomUUID().toString();
        ProductResponse product = new ProductResponse(id, productRequest.name(), productRequest.price());
        products.put(id, product);

        EntityModel<ProductResponse> entityModel = toModel(product);
        return ResponseEntity.created(linkTo(methodOn(ProductController.class).get(id)).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public EntityModel<ProductResponse> update(@PathVariable String id, @RequestBody ProductRequest productRequest) {
        if (!products.containsKey(id)) {
            throw new NoSuchElementException("Product not found");
        }

        ProductResponse newProduct = new ProductResponse(id, productRequest.name(), productRequest.price());
        products.put(id, newProduct);
        return toModel(newProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!products.containsKey(id)) {
            throw new NoSuchElementException("Product not found");
        }
        products.remove(id);
        return ResponseEntity.noContent().build();
    }


    private EntityModel<ProductResponse> toModel(ProductResponse product) {
        return EntityModel.of(product,
                linkTo(methodOn(ProductController.class).get(product.id())).withSelfRel(),
                linkTo(methodOn(ProductController.class).list()).withRel("products"));
    }
}
