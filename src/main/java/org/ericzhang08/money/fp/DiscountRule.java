package org.ericzhang08.money.fp;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Predicate;

@Component
public class DiscountRule implements Predicate<BigDecimal> {

    @Override
    public boolean test(BigDecimal bigDecimal) {
        return false;
    }
}
