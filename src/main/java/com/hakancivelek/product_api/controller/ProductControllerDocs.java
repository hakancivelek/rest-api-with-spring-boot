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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Tag(name = "Product API", description = "API for managing products")
public interface ProductControllerDocs {

    @Operation(summary = "Get product by ID", description = "Retrieve a product by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Product not found\"}")))
    })
    @GetMapping("/{id}")
    EntityModel<ProductResponse> get(@PathVariable String id);

    @Operation(summary = "Get all products", description = "Retrieve all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products found")
    })
    @GetMapping
    CollectionModel<EntityModel<ProductResponse>> getAll();

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
    ResponseEntity<EntityModel<ProductResponse>> create(@Valid @RequestBody CreateProductRequest createProductRequest);

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
    EntityModel<ProductResponse> update(@PathVariable String id, @RequestBody UpdateRequestBody updateRequestBody);

    @Operation(summary = "Delete a product", description = "Delete a product by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(value = "{\"error\": \"Product not found\"}")))
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable String id);

    default EntityModel<ProductResponse> toModel(ProductResponse product) {
        return EntityModel.of(
                product,
                linkTo(methodOn(ProductController.class).get(product.id())).withSelfRel(),
                linkTo(methodOn(ProductController.class).getAll()).withRel("products"),
                linkTo(methodOn(ProductController.class).update(product.id(), null)).withRel("update"),
                linkTo(methodOn(ProductController.class).delete(product.id())).withRel("delete")
        );
    }
}