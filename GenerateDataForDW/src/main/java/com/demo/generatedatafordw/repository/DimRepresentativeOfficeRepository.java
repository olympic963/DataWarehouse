package com.demo.generatedatafordw.repository;

//import com.example.datagenerator.model.DimRepresentativeOffice;
import com.demo.generatedatafordw.model.DimRepresentativeOffice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimRepresentativeOfficeRepository extends JpaRepository<DimRepresentativeOffice, Integer> {
}
