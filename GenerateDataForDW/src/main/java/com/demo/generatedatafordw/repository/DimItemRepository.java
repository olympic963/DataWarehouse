package com.demo.generatedatafordw.repository;

//import com.example.datagenerator.model.DimItem;
import com.demo.generatedatafordw.model.DimItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimItemRepository extends JpaRepository<DimItem, Integer> {
}