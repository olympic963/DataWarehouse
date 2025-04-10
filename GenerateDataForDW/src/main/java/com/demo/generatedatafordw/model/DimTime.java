package com.demo.generatedatafordw.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "DimTime")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DimTime {
    @Id
    @Column(name = "time_id")
    private Integer timeId;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "quarter", nullable = false)
    private Byte quarter;

    @Column(name = "month", nullable = false)
    private Byte month;

    @Column(name = "day", nullable = false)
    private Byte day;
}
