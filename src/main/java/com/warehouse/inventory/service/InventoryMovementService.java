package com.warehouse.inventory.service;

import com.warehouse.inventory.entity.InventoryMovement;
import com.warehouse.inventory.entity.Product;
import com.warehouse.inventory.repository.InventoryMovementRepository;
import com.warehouse.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class InventoryMovementService {

    private final InventoryMovementRepository movementRepository;
    private final ProductRepository productRepository;

    public InventoryMovementService(InventoryMovementRepository movementRepository,
                                    ProductRepository productRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public InventoryMovement recordMovement(Long productId, String movementType, Integer quantity, String remarks) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        if ("INWARD".equalsIgnoreCase(movementType)) {
            product.setCurrentStock(product.getCurrentStock() + quantity);
        } else if ("OUTWARD".equalsIgnoreCase(movementType)) {
            if (product.getCurrentStock() < quantity) {
                throw new RuntimeException("Insufficient stock for product: " + product.getSku());
            }
            product.setCurrentStock(product.getCurrentStock() - quantity);
        } else {
            throw new IllegalArgumentException("Invalid movement type. Must be INWARD or OUTWARD.");
        }

        productRepository.save(product);

        InventoryMovement movement = new InventoryMovement();
        movement.setProduct(product);
        movement.setMovementType(movementType.toUpperCase());
        movement.setQuantity(quantity);
        movement.setMovementDate(LocalDateTime.now());
        movement.setRemarks(remarks);

        InventoryMovement savedMovement = movementRepository.save(movement);

        if (product.getCurrentStock() <= product.getMinimumStockThreshold()) {
            System.err.println("ALERT: Low Stock detected for SKU [" + product.getSku() +
                    "] - " + product.getName() +
                    ". Current Stock: " + product.getCurrentStock());
        }

        return savedMovement;
    }
}