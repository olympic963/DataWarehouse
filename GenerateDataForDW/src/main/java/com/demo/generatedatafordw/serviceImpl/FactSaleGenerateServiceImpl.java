package com.demo.generatedatafordw.serviceImpl;

import com.demo.generatedatafordw.model.DimCustomer;
import com.demo.generatedatafordw.model.DimItem;
import com.demo.generatedatafordw.model.DimTime;
import com.demo.generatedatafordw.model.FactSale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.generatedatafordw.repository.DimCustomerRepository;
import com.demo.generatedatafordw.repository.DimItemRepository;
import com.demo.generatedatafordw.repository.DimTimeRepository;
import com.demo.generatedatafordw.repository.FactSaleRepository;
import com.demo.generatedatafordw.service.FactSaleGenerateService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class FactSaleGenerateServiceImpl implements FactSaleGenerateService {

    @Autowired
    private FactSaleRepository factSaleRepository;

    @Autowired
    private DimTimeRepository timeRepository;

    @Autowired
    private DimItemRepository itemRepository;

    @Autowired
    private DimCustomerRepository customerRepository;

    private final Random random = new Random();
    private static final BigDecimal MIN_COEFFICIENT = new BigDecimal("1");  // Giá gốc
    private static final BigDecimal MAX_COEFFICIENT = new BigDecimal("1.3");  // Tăng tối đa 30%
    private static final Logger log = LoggerFactory.getLogger(FactSaleGenerateServiceImpl.class);
    @Override
    @Transactional
    public void generateData() {
        List<DimTime> times = timeRepository.findAll();
        List<DimItem> items = itemRepository.findAll();
        List<DimCustomer> customers = customerRepository.findAll();
        log.info("Found {} DimTime records", times.size());
        log.info("Found {} DimCustomer records", customers.size());
        log.info("Found {} DimItem records", items.size());

        int totalRecords = 2000000;
        int threads = 10;
        int recordsPerThread = totalRecords / threads;

        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<?>> futures = new ArrayList<>();

        for (int t = 0; t < threads; t++) {
            int start = t * recordsPerThread;
            int end = (t == threads - 1) ? totalRecords : (t + 1) * recordsPerThread;

            futures.add(executor.submit(() -> {
                List<FactSale> batchList = new ArrayList<>();
                int batchSize = 1000;

                for (int i = start; i < end; i++) {
                    int unitsSold = random.nextInt(4) + 1;
                    DimItem randomItem = items.get(random.nextInt(items.size()));
                    DimTime randomTime = times.get(random.nextInt(times.size()));
                    DimCustomer randomCustomer = customers.get(random.nextInt(customers.size()));

                    // Tạo các đối tượng chỉ chứa ID để tránh SELECT
                    DimItem itemRef = new DimItem();
                    itemRef.setItemId(randomItem.getItemId());

                    DimTime timeRef = new DimTime();
                    timeRef.setTimeId(randomTime.getTimeId());

                    DimCustomer customerRef = new DimCustomer();
                    customerRef.setCustomerId(randomCustomer.getCustomerId());

                    BigDecimal randomCoefficient = MIN_COEFFICIENT.add(
                            MAX_COEFFICIENT.subtract(MIN_COEFFICIENT)
                                    .multiply(BigDecimal.valueOf(random.nextDouble()))
                    ).setScale(2, BigDecimal.ROUND_HALF_UP);

                    BigDecimal revenue = randomItem.getPrice()
                            .multiply(BigDecimal.valueOf(unitsSold))
                            .multiply(randomCoefficient)
                            .setScale(2, BigDecimal.ROUND_HALF_UP);

                    BigDecimal avgSales = revenue
                            .divide(BigDecimal.valueOf(unitsSold), 5, BigDecimal.ROUND_HALF_UP);

                    FactSale sale = FactSale.builder()
                            .time(timeRef)
                            .item(itemRef)
                            .customer(customerRef)
                            .unitsSold(unitsSold)
                            .revenue(revenue)
                            .avgSales(avgSales)
                            .build();

                    batchList.add(sale);

                    if (batchList.size() >= batchSize) {
                        factSaleRepository.saveAll(batchList);
                        batchList.clear();
                    }
                }

                if (!batchList.isEmpty()) {
                    factSaleRepository.saveAll(batchList);
                }
            }));
        }
        futures.forEach(f -> {
            try {
                f.get();
            } catch (Exception e) {
                log.error("Error in thread execution", e);
            }
        });
        executor.shutdown();
    }
}