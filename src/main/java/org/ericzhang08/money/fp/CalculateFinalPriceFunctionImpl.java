package org.ericzhang08.money.fp;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.Predicate;

@Component
public class CalculateFinalPriceFunctionImpl implements CalculateFinalPriceFunction {
    @Override
    public BigDecimal apply(
            Function<BigDecimal, BigDecimal> applyDiscount,
            Function<BigDecimal, BigDecimal> applyTax,
            Function<BigDecimal, Boolean> discountRule,
            BigDecimal listingPrice) {
        if (discountRule.apply(listingPrice)) {
            return applyTax.compose(applyDiscount).apply(listingPrice);
        }
        return listingPrice;
    }
}
