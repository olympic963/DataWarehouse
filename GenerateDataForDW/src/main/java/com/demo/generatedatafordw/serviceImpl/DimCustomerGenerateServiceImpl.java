package com.demo.generatedatafordw.serviceImpl;

import com.github.javafaker.Faker;
import com.demo.generatedatafordw.model.DimCustomer;
import com.demo.generatedatafordw.model.DimRepresentativeOffice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.generatedatafordw.repository.DimCustomerRepository;
import com.demo.generatedatafordw.repository.DimRepresentativeOfficeRepository;
import com.demo.generatedatafordw.service.DimCustomerGenerateService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DimCustomerGenerateServiceImpl implements DimCustomerGenerateService {

    @Autowired
    private DimCustomerRepository customerRepository;

    @Autowired
    private DimRepresentativeOfficeRepository officeRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private static final Logger log = LoggerFactory.getLogger(DimCustomerGenerateServiceImpl.class);
    private static final int BATCH_SIZE = 1000; // Kích thước lô, có thể điều chỉnh

    @Override
    public void generateData() {
        List<DimRepresentativeOffice> offices = officeRepository.findAll();
        log.info("Found {} DimRepresentativeOffice records", offices.size());
        if (offices.isEmpty()) {
            throw new IllegalStateException("No DimRepresentativeOffice data available to generate DimCustomer.");
        }

        List<DimCustomer> customerBatch = new ArrayList<>(BATCH_SIZE);
        int totalRecords = 15000;

        for (int i = 1; i <= totalRecords; i++) {
            DimCustomer customer = DimCustomer.builder()
                    .customerId(i)
                    .customerName(faker.name().fullName())
                    .customerType(random.nextInt(3)) // Loại khách hàng từ 0-2
                    .city(offices.get(random.nextInt(offices.size()))) // Gán đối tượng DimRepresentativeOffice
                    .build();

            customerBatch.add(customer);

            // Khi danh sách đạt kích thước lô hoặc là bản ghi cuối cùng
            if (customerBatch.size() == BATCH_SIZE || i == totalRecords) {
                customerRepository.saveAll(customerBatch);
                customerBatch.clear(); // Xóa danh sách để giải phóng bộ nhớ
                log.info("Saved batch of {} customers at record {}", BATCH_SIZE, i);
            }
        }

        long customerCount = customerRepository.count(); // Thay findAll() bằng count()
        log.info("Total DimCustomer records in database: {}", customerCount);
    }
}