package com.demo.generatedatafordw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "DimItem")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DimItem {

    @Id
    @Column(name = "item_id")
    private Integer itemId;

    @Column(name = "description")
    private String description;

    @Column(name = "price", precision = 15, scale = 2)
    private BigDecimal price;
}
