package com.ashcheglov.domain.trade;

import com.ashcheglov.domain.stock.Stock;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import static java.time.LocalDateTime.now;

/**
 * @author Anton
 * @since 18.09.2016
 */
public class TradeFactory {

    private static AtomicLong idGenerator = new AtomicLong();

    /**
     * Returns a new instance of {@link Trade} with a unique ID.
     *
     * @param type      a type of the operation
     * @param stock     a stock on which the operation was performed
     * @param quantity  a quantity of stocks
     * @param price     a price per stock
     * @return          a new unique Trade
     */
    public Trade newInstance(TradeType type, Stock stock,
                             long quantity, BigDecimal price) {
        return new Trade(
                idGenerator.getAndIncrement(), type, stock,
                quantity, price, now()
        );
    }

}
