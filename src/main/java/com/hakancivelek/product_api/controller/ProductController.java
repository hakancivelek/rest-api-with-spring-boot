package com.hakancivelek.product_api.controller;

import com.hakancivelek.product_api.dto.CreateProductRequest;
import com.hakancivelek.product_api.dto.ProductResponse;
import com.hakancivelek.product_api.dto.UpdateRequestBody;
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
public class ProductController implements ProductControllerDocs {
    private final Map<String, ProductResponse> products = new HashMap<>();

    @Override
    public EntityModel<ProductResponse> get(String id) {
        ProductResponse product = products.get(id);
        if (product == null) {
            throw new NoSuchElementException("Product not found");
        }
        return toModel(product);
    }

    @Override
    public CollectionModel<EntityModel<ProductResponse>> getAll() {
        List<EntityModel<ProductResponse>> productsResponse = products.values().stream().map(this::toModel).toList();
        return CollectionModel.of(productsResponse, linkTo(methodOn(ProductController.class).getAll()).withSelfRel());
    }

    @Override
    public ResponseEntity<EntityModel<ProductResponse>> create(@Valid @RequestBody CreateProductRequest createProductRequest) {
        String id = UUID.randomUUID().toString();
        ProductResponse product = new ProductResponse(id, createProductRequest.name(), createProductRequest.price());
        products.put(id, product);

        EntityModel<ProductResponse> entityModel = toModel(product);
        return ResponseEntity.created(linkTo(methodOn(ProductController.class).get(id)).toUri()).body(entityModel);
    }

    @Override
    public EntityModel<ProductResponse> update(String id, UpdateRequestBody updateRequestBody) {
        if (!products.containsKey(id)) {
            throw new NoSuchElementException("Product not found");
        }

        ProductResponse newProduct = new ProductResponse(id, updateRequestBody.name(), updateRequestBody.price());
        products.put(id, newProduct);
        return toModel(newProduct);
    }

    @Override
    public ResponseEntity<Void> delete(String id) {
        if (!products.containsKey(id)) {
            throw new NoSuchElementException("Product not found");
        }
        products.remove(id);
        return ResponseEntity.noContent().build();
    }

}