package com.demo.generatedatafordw.repository;

import com.demo.generatedatafordw.model.FactSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactSaleRepository extends JpaRepository<FactSale, FactSale.FactSaleId> {
}
