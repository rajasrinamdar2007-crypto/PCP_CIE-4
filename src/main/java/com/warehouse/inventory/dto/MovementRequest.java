package com.warehouse.inventory.dto;

import lombok.Data;

@Data
public class MovementRequest {
    private Long productId;
    private String movementType; // "INWARD" or "OUTWARD"
    private Integer quantity;
    private String remarks;
}
