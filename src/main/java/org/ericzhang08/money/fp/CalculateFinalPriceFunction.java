package org.ericzhang08.money.fp;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface CalculateFinalPriceFunction {
    BigDecimal apply(Function<BigDecimal, BigDecimal> applyDiscount, Function<BigDecimal, BigDecimal> applyTax,
                     Function<BigDecimal, Boolean> discountRule, BigDecimal listingPrice);
}
