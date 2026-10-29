package com.warehouse.inventory.controller;

import com.warehouse.inventory.dto.MovementRequest;
import com.warehouse.inventory.entity.InventoryMovement;
import com.warehouse.inventory.service.InventoryMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/movements")
public class InventoryMovementController {

    private final InventoryMovementService movementService;

    public InventoryMovementController(InventoryMovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public ResponseEntity<InventoryMovement> recordMovement(@RequestBody MovementRequest request) {
        InventoryMovement movement = movementService.recordMovement(
                request.getProductId(),
                request.getMovementType(),
                request.getQuantity(),
                request.getRemarks()
        );
        return new ResponseEntity<>(movement, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InventoryMovement>> getAllMovements() {
        return ResponseEntity.ok(movementService.getAllMovements());
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<InventoryMovement>> getMovementsByProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(movementService.getMovementsByProductId(productId));
    }
}