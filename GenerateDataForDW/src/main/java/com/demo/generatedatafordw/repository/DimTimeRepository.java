package com.demo.generatedatafordw.repository;

//import com.example.datagenerator.model.DimTime;
import com.demo.generatedatafordw.model.DimTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimTimeRepository extends JpaRepository<DimTime, Integer> {
}
