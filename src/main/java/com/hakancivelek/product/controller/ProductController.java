package com.hakancivelek.product.controller;

import com.hakancivelek.product.dto.CreateProductRequest;
import com.hakancivelek.product.dto.ProductResponse;
import com.hakancivelek.product.dto.UpdateRequestBody;
import com.hakancivelek.product.service.ProductService;
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
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public EntityModel<ProductResponse> get(Long id) {
        return toModel(productService.get(id));
    }

    @Override
    public CollectionModel<EntityModel<ProductResponse>> getAll() {
        List<EntityModel<ProductResponse>> productsResponse = productService.getAll()
                .stream()
                .map(this::toModel)
                .toList();

        return CollectionModel.of(
                productsResponse,
                linkTo(methodOn(ProductController.class).getAll()).withSelfRel()
        );
    }

    @Override
    public ResponseEntity<EntityModel<ProductResponse>> create(
            @Valid @RequestBody CreateProductRequest createProductRequest) {

        ProductResponse product = productService.create(createProductRequest);

        EntityModel<ProductResponse> entityModel = toModel(product);

        return ResponseEntity
                .created(linkTo(methodOn(ProductController.class).get(product.id())).toUri())
                .body(entityModel);
    }

    @Override
    public EntityModel<ProductResponse> update(Long id,
                                               @Valid @RequestBody UpdateRequestBody updateRequestBody) {
        ProductResponse updatedProduct = productService.update(id, updateRequestBody);
        return toModel(updatedProduct);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}