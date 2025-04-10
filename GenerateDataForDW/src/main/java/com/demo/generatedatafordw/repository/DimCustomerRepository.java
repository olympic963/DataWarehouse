package com.demo.generatedatafordw.repository;

//import com.example.datagenerator.model.DimCustomer;
import com.demo.generatedatafordw.model.DimCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimCustomerRepository extends JpaRepository<DimCustomer, Integer> {
}
