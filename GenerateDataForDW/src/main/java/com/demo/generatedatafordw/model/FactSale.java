package com.demo.generatedatafordw.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "FactSale")
@IdClass(FactSale.FactSaleId.class) // Tham chiếu đến lớp lồng nhau
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FactSale {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "time_id")
    private DimTime time;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id")
    private DimItem item;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id")
    private DimCustomer customer;

    @Column(name = "units_sold")
    private Integer unitsSold;

    @Column(name = "revenue", precision = 15, scale = 2)
    private BigDecimal revenue;

    @Column(name = "avg_sales", precision = 15, scale = 5)
    private BigDecimal avgSales;

    // Lớp lồng nhau FactSaleId
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FactSaleId implements Serializable {
        private Integer time;    // Khớp với time_id
        private Integer item;    // Khớp với item_id
        private Integer customer; // Khớp với customer_id
    }
}