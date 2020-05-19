package org.ericzhang08.money.fp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

@Configuration
public class FpConfiguration {

    @Bean
    public Function<BigDecimal, BigDecimal> calculateFinalPriceForListingPrice(
            @Value("${money.discount.rate}") String discountRateString,
            @Value("${money.tax.rate}") String taxRateString,
            @Value("${money.limit}") String discountLimitString,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyDiscountFunction,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyTaxFunction,
            Function<BigDecimal, Boolean>  discountRule,
            CalculateFinalPriceFunction calculateFinalPriceFunction
    ) {
        var discountRate = new BigDecimal(discountRateString);
        var taxRate = new BigDecimal(taxRateString);
        var discountLimit = new BigDecimal(discountLimitString);
        return generateCurriedCalculateFinalPrice(discountRate, taxRate, discountLimit, applyDiscountFunction, applyTaxFunction, discountRule, calculateFinalPriceFunction);
    }

    private Function<BigDecimal, BigDecimal> generateCurriedCalculateFinalPrice(
            BigDecimal discountRate,
            BigDecimal taxRate,
            BigDecimal discountLimit,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyDiscount,
            BiFunction<BigDecimal, BigDecimal, BigDecimal> applyTax,
            BiFunction<BigDecimal, BigDecimal, Boolean> discountRule,
            CalculateFinalPriceFunction calculateFinalPriceFunction
    ) {
        var applyDiscountForAmount = curry(applyDiscount).apply(discountRate);
        var applyTaxForAmount = curry(applyTax).apply(taxRate);
        var applyDiscountRule = curry2(discountRule).apply(discountLimit);
        var calculateFinalPriceForListingPrice = curry(calculateFinalPriceFunction)
                .apply(applyDiscountForAmount)
                .apply(applyTaxForAmount);
        return calculateFinalPriceForListingPrice;
    }

    private Function<BigDecimal, Function<BigDecimal, BigDecimal>>
    curry(BiFunction<BigDecimal, BigDecimal, BigDecimal> function) {
        return t -> u -> function.apply(t, u);
    }

    private Function<BigDecimal, Function<BigDecimal, Boolean>>
    curry2(BiFunction<BigDecimal, BigDecimal, Boolean> function) {
        return t -> u -> function.apply(t, u);
    }

    private Function<Function<BigDecimal, BigDecimal>, Function<Function<BigDecimal, BigDecimal>, Function<BigDecimal, BigDecimal>>>
    curry(CalculateFinalPriceFunction function) {
        return applyDiscountForAmount -> applyTaxForAmount -> listingPrice ->
                function.apply(applyDiscountForAmount, applyTaxForAmount, listingPrice);
    }
}
