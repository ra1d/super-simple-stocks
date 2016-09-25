package com.ashcheglov.domain.stock;

import java.math.BigDecimal;

/**
 * TODO equals, hashCode?
 * @author Anton
 * @since 17.09.2016
 */
public class CommonStock implements Stock {

    private final String symbol;
    private final StockType type;
    private final BigDecimal lastDividend;
    private final BigDecimal parValue;

    public CommonStock(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
        this.symbol = symbol;
        this.type = StockType.COMMON;
        this.lastDividend = lastDividend;
        this.parValue = parValue;
    }

    public String getSymbol() {
        return symbol;
    }

    public StockType getType() {
        return type;
    }

    public BigDecimal getLastDividend() {
        return lastDividend;
    }

    public BigDecimal getParValue() {
        return parValue;
    }

    @Override
    public BigDecimal calculateDividendYield(BigDecimal price) {
        return lastDividend.divide(price);
    }

    @Override
    public BigDecimal calculatePERatio(BigDecimal price) {
        return price.divide(lastDividend);
    }

}
