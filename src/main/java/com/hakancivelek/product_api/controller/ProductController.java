package com.hakancivelek.product_api.controller;

import com.hakancivelek.product_api.dto.CreateProductRequest;
import com.hakancivelek.product_api.dto.ProductResponse;
import com.hakancivelek.product_api.dto.UpdateRequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Product API", description = "API for managing products")
@RestController
@RequestMapping("api/products")
public class ProductController {
    private final Map<String, ProductResponse> products = new HashMap<>();

    @Operation(summary = "Get product by ID", description = "Retrieve a product by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Product not found\"}")))
    })
    @GetMapping("/{id}")
    public EntityModel<ProductResponse> get(@PathVariable String id) {
        ProductResponse product = products.get(id);
        if (product == null) {
            throw new NoSuchElementException("Product not found");
        }
        return toModel(product);
    }

    @Operation(summary = "Get all products", description = "Retrieve all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found")
    })
    @GetMapping
    public CollectionModel<EntityModel<ProductResponse>> getAll() {
        List<EntityModel<ProductResponse>> productsResponse = products.values().stream().map(this::toModel).toList();
        return CollectionModel.of(productsResponse, linkTo(methodOn(ProductController.class).getAll()).withSelfRel());
    }

    @Operation(summary = "Create a new product", description = "Create a new product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"name\": \"Name cannot be blank\", " +
                                    "\"price\": \"Price must be non-negative\"}")))
    })
    @PostMapping
    public ResponseEntity<EntityModel<ProductResponse>> create(@Valid @RequestBody CreateProductRequest createProductRequest) {
        String id = UUID.randomUUID().toString();
        ProductResponse product = new ProductResponse(id, createProductRequest.name(), createProductRequest.price());
        products.put(id, product);

        EntityModel<ProductResponse> entityModel = toModel(product);
        return ResponseEntity.created(linkTo(methodOn(ProductController.class).get(id)).toUri()).body(entityModel);
    }

    @Operation(summary = "Update an existing product", description = "Update an existing product with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"name\": \"Name cannot be blank\", " +
                                    "\"price\": \"Price must be greater than or equal to zero\"}"))),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Product not found\"}")))
    })
    @PutMapping("/{id}")
    public EntityModel<ProductResponse> update(@PathVariable String id, @RequestBody UpdateRequestBody updateRequestBody) {
        if (!products.containsKey(id)) {
            throw new NoSuchElementException("Product not found");
        }

        ProductResponse newProduct = new ProductResponse(id, updateRequestBody.name(), updateRequestBody.price());
        products.put(id, newProduct);
        return toModel(newProduct);
    }

    @Operation(summary = "Delete a product", description = "Delete a product by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Product not found\"}")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (!products.containsKey(id)) {
            throw new NoSuchElementException("Product not found");
        }
        products.remove(id);
        return ResponseEntity.noContent().build();
    }


    private EntityModel<ProductResponse> toModel(ProductResponse product) {
        return EntityModel.of(product, linkTo(methodOn(ProductController.class).get(product.id())).withSelfRel(), linkTo(methodOn(ProductController.class).getAll()).withRel("products"), linkTo(methodOn(ProductController.class).update(product.id(), null)).withRel("update"), linkTo(methodOn(ProductController.class).delete(product.id())).withRel("delete"));
    }
}