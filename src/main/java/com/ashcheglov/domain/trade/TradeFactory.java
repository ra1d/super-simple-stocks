package com.ashcheglov.domain.trade;

import com.ashcheglov.domain.stock.Stock;

import java.math.BigDecimal;

/**
 * @author Anton
 * @since 18.09.2016
 */
public class TradeFactory {

    public Trade newInstance(TradeType tradeType, Stock stock,
                             long quantity, BigDecimal price) {
        return null;
    }

}
