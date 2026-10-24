package com.warehouse.inventory.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data // Lombok generates getters, setters, toString, etc.
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sku;

    @Column(nullable = false)
    private String name;

    private Integer currentStock;
    private Integer minimumStockThreshold;
    private Double price;
}