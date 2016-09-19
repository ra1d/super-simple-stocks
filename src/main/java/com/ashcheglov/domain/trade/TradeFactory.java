package com.ashcheglov.domain.trade;

import com.ashcheglov.domain.stock.Stock;

import java.math.BigDecimal;

import static java.time.LocalDateTime.now;

/**
 * @author Anton
 * @since 18.09.2016
 */
public class TradeFactory {

    public Trade newInstance(TradeType tradeType, Stock stock,
                             long quantity, BigDecimal price) {
        return new Trade(tradeType, stock, quantity, price, now());
    }

}
