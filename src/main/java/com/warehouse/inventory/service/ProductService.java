package com.warehouse.inventory.service;

import com.warehouse.inventory.entity.Product;
import com.warehouse.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        if (product.getCurrentStock() == null) {
            product.setCurrentStock(0);
        }
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }
}