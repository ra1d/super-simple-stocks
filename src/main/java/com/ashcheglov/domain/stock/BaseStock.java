package com.ashcheglov.domain.stock;

import java.math.BigDecimal;

/**
 * A base class encapsulating the properties common for all stocks.
 *
 * @author Anton
 * @since 25.09.2016
 */
public abstract class BaseStock implements Stock {

    private final String symbol;
    private final StockType type;
    private final BigDecimal lastDividend;
    private final BigDecimal parValue;

    public BaseStock(String symbol, StockType type,
                     BigDecimal lastDividend, BigDecimal parValue) {
        this.symbol = symbol;
        this.type = type;
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
    public boolean equals(Object that) {
        if (this == that) return true;
        if (!(that instanceof BaseStock)) return false;
        BaseStock baseStock = (BaseStock) that;
        if (!symbol.equals(baseStock.symbol)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return symbol.hashCode();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BaseStock{");
        sb.append("symbol='").append(symbol).append('\'');
        sb.append(", type=").append(type);
        sb.append(", lastDividend=").append(lastDividend);
        sb.append(", parValue=").append(parValue);
        sb.append('}');
        return sb.toString();
    }

}
