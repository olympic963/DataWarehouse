package com.demo.generatedatafordw.serviceImpl;

import com.github.javafaker.Faker;
import com.demo.generatedatafordw.model.DimItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.generatedatafordw.repository.DimItemRepository;
import com.demo.generatedatafordw.service.DimItemGenerateService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DimItemGenerateServiceImpl implements DimItemGenerateService {

    @Autowired
    private DimItemRepository itemRepository;

    private final Faker faker = new Faker();
    private final Random random = new Random();
    private static final int BATCH_SIZE = 50; // Kích thước lô, điều chỉnh theo nhu cầu

    @Override
    public void generateData() {
        List<DimItem> itemBatch = new ArrayList<>(BATCH_SIZE);
        int totalRecords = 100; // Sinh 100 sản phẩm

        for (int i = 1; i <= totalRecords; i++) {
            DimItem item = DimItem.builder()
                    .itemId(i)
                    .description(faker.commerce().productName())
                    .price(BigDecimal.valueOf(random.nextDouble() * 40 + 10).setScale(2, BigDecimal.ROUND_HALF_UP))
                    .build();

            itemBatch.add(item);

            // Khi danh sách đạt kích thước lô hoặc là bản ghi cuối cùng
            if (itemBatch.size() == BATCH_SIZE || i == totalRecords) {
                itemRepository.saveAll(itemBatch);
                itemBatch.clear(); // Xóa danh sách để giải phóng bộ nhớ
            }
        }

        long itemCount = itemRepository.count(); // Kiểm tra số lượng bản ghi
        System.out.println("Total DimItem records in database: " + itemCount);
    }
}