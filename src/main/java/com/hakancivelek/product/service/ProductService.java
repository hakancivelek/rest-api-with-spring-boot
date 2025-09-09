package com.hakancivelek.product.service;

import com.hakancivelek.product.entity.Product;
import com.hakancivelek.product.dto.CreateProductRequest;
import com.hakancivelek.product.dto.ProductResponse;
import com.hakancivelek.product.dto.UpdateRequestBody;
import com.hakancivelek.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse get(Long id) {
        return productRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));
    }

    public List<ProductResponse> getAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponse create(CreateProductRequest request) {
        Product product = new Product(
                null,
                request.name(),
                request.price(),
                request.description(),
                request.sku(),
                request.barcode()
        );
        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public ProductResponse update(Long id, UpdateRequestBody request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

        product.setName(request.name())
                .setPrice(request.price())
                .setDescription(request.description())
                .setSku(request.sku())
                .setBarcode(request.barcode());

        Product updated = productRepository.save(product);
        return toResponse(updated);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NoSuchElementException("Product not found");
        }
        productRepository.deleteById(id);
    }

    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getSku(),
                product.getBarcode()
        );
    }
}
