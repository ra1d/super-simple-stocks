package com.ashcheglov.domain.trade;

import com.ashcheglov.domain.stock.Stock;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import static java.time.LocalDateTime.now;

/**
 * New trades must be created via this factory.
 *
 * @author Anton
 * @since 18.09.2016
 */
@Component
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
