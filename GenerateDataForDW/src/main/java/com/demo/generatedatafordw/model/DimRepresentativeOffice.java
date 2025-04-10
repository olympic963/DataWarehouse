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
@Table(name = "DimRepresentativeOffice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DimRepresentativeOffice {
    @Id
    @Column(name = "city_id")
    private Integer cityId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "state")
    private String state;
}
