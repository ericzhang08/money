package org.ericzhang08.money.fp;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.BiFunction;

@Component
public class ApplyDiscountRuleFunction implements BiFunction<BigDecimal, BigDecimal, Boolean> {
    @Override
    public Boolean apply(BigDecimal discountLimit, BigDecimal amount) {
        return discountLimit.compareTo(amount) < 0;
    }
}
