package com.ashcheglov.domain.stock;

import java.math.BigDecimal;

import static com.ashcheglov.domain.stock.StockType.COMMON;

/**
 * A common stock.
 *
 * @author Anton
 * @since 17.09.2016
 */
public class CommonStock
        extends BaseStock implements Stock {

    public CommonStock(String symbol, BigDecimal lastDividend, BigDecimal parValue) {
        super(symbol, COMMON, lastDividend, parValue);
    }

    @Override
    public BigDecimal calculateDividendYield(BigDecimal price) {
        return getLastDividend().divide(price);
    }

    @Override
    public BigDecimal calculatePERatio(BigDecimal price) {
        return price.divide(getLastDividend());
    }

}
