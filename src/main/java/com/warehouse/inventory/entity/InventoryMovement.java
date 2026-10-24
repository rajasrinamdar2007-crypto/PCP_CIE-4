package com.warehouse.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory_movements")
@Data
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String movementType; // "INWARD" or "OUTWARD"

    @Column(nullable = false)
    private Integer quantity;

    private LocalDateTime movementDate;
    private String remarks;
}
