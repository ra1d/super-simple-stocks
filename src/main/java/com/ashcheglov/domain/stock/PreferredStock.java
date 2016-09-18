package com.ashcheglov.domain.stock;

import java.math.BigDecimal;

/**
 * @author Anton
 * @since 18.09.2016
 */
public class PreferredStock extends CommonStock implements Stock {

    private final StockType type;
    private final BigDecimal fixedDividend;

    public PreferredStock(String symbol, BigDecimal lastDividend,
                          BigDecimal fixedDividend, BigDecimal parValue) {
        super(symbol, lastDividend, parValue);
        this.type = StockType.PREFERRED;
        this.fixedDividend = fixedDividend;
    }

    @Override
    public StockType getType() {
        return type;
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
