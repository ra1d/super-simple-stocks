package com.ashcheglov.domain.stock;

import java.math.BigDecimal;

import static com.ashcheglov.domain.stock.StockType.PREFERRED;

/**
 * A preferred stock.
 *
 * @author Anton
 * @since 18.09.2016
 */
public class PreferredStock extends BaseStock {

    private final BigDecimal fixedDividend;

    public PreferredStock(String symbol, BigDecimal lastDividend,
                          BigDecimal fixedDividend, BigDecimal parValue) {
        super(symbol, PREFERRED, lastDividend, parValue);
        this.fixedDividend = fixedDividend;
    }

    public BigDecimal getFixedDividend() {
        return fixedDividend;
    }

    @Override
    public BigDecimal calculateDividendYield(BigDecimal price) {
        return (fixedDividend.multiply(this.getParValue())).divide(price);
    }

    @Override
    public BigDecimal calculatePERatio(BigDecimal price) {
        return price.divide(fixedDividend.multiply(this.getParValue()));
    }

}
