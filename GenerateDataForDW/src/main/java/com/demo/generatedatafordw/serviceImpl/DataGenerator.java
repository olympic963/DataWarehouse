package com.demo.generatedatafordw.serviceImpl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.demo.generatedatafordw.service.DimCustomerGenerateService;
import com.demo.generatedatafordw.service.DimItemGenerateService;
import com.demo.generatedatafordw.service.DimTimeGenerateService;
import com.demo.generatedatafordw.service.FactSaleGenerateService;

@Component
public class DataGenerator implements CommandLineRunner {

    private final DimTimeGenerateService timeGenerateService;
    private final DimItemGenerateService itemGenerateService;
    private final DimCustomerGenerateService customerGenerateService;
    private final FactSaleGenerateService factSaleGenerateService;

    @Autowired
    public DataGenerator(
            DimTimeGenerateService timeGenerateService,
            DimItemGenerateService itemGenerateService,
            DimCustomerGenerateService customerGenerateService,
            FactSaleGenerateService factSaleGenerateService) {
        this.timeGenerateService = timeGenerateService;
        this.itemGenerateService = itemGenerateService;
        this.customerGenerateService = customerGenerateService;
        this.factSaleGenerateService = factSaleGenerateService;
    }

    @Override
    public void run(String... args) throws Exception {
        // Gọi tuần tự để đảm bảo phụ thuộc dữ liệu
        timeGenerateService.generateData();    // Sinh DimTime trước
        itemGenerateService.generateData();    // Sinh DimItem
        customerGenerateService.generateData(); // Sinh DimCustomer (dùng DimRepresentativeOffice)
        factSaleGenerateService.generateData(); // Sinh FactSale (dùng các bảng khác)
    }
}
