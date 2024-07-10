package com.app.lms.store;

import com.app.lms.repository.LmsConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class LmsConfigStore {

    private final LmsConfigRepository lmsConfigRepository;

    public Integer getMonthlySettlementDate() {
        String key = "monthly_settlement_date";
        String value = lmsConfigRepository.getKey(key);
        return Integer.parseInt(value);
    }

    public Integer getWeeklySettlementDay() {
        String key = "weekly_settlement_day";
        String value = lmsConfigRepository.getKey(key);
        return Integer.parseInt(value);
    }

    public BigDecimal getTotalMoneyInPool() {
        String key = "total_money_in_pool";
        String value = lmsConfigRepository.getKey(key);
        return new BigDecimal(value);
    }
}
