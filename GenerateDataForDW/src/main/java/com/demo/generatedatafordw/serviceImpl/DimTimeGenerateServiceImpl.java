package com.demo.generatedatafordw.serviceImpl;

import com.demo.generatedatafordw.model.DimTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.generatedatafordw.repository.DimTimeRepository;
import com.demo.generatedatafordw.service.DimTimeGenerateService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DimTimeGenerateServiceImpl implements DimTimeGenerateService {

    @Autowired
    private DimTimeRepository timeRepository;

    private static final int BATCH_SIZE = 365; // Kích thước lô, điều chỉnh theo nhu cầu
    private static final DateTimeFormatter BASIC_ISO_DATE = DateTimeFormatter.BASIC_ISO_DATE;

    @Override
    public void generateData() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        int totalDays = 2 * 365; // 2 năm dữ liệu
        List<DimTime> timeBatch = new ArrayList<>(BATCH_SIZE);

        for (int i = 0; i < totalDays; i++) {
            LocalDate date = startDate.plusDays(i);
            DimTime time = new DimTime();
            time.setTimeId(Integer.parseInt(date.format(BASIC_ISO_DATE)));
            time.setYear(date.getYear());
            time.setQuarter((byte) ((date.getMonthValue() - 1) / 3 + 1));
            time.setMonth((byte) date.getMonthValue());
            time.setDay((byte) date.getDayOfMonth());

            timeBatch.add(time);

            // Khi danh sách đạt kích thước lô hoặc là bản ghi cuối cùng
            if (timeBatch.size() == BATCH_SIZE || i == totalDays - 1) {
                timeRepository.saveAll(timeBatch);
                timeBatch.clear(); // Xóa danh sách để giải phóng bộ nhớ
            }
        }

        long timeCount = timeRepository.count(); // Kiểm tra số lượng bản ghi
        System.out.println("Total DimTime records in database: " + timeCount);
    }
}