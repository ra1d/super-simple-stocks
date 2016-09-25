package com.ashcheglov.dao;

import com.ashcheglov.domain.stock.Stock;
import com.ashcheglov.domain.trade.Trade;
import com.ashcheglov.domain.trade.TradeType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

/**
 * @author Anton
 * @since 18.09.2016
 */
public interface TradeDao {

    /**
     * Saves a trade, i.e. an operation with a given quantity of stocks.
     *
     * @param trade a trade record to save
     * @return      true if the given trade was saved
     */
    boolean save(Trade trade);

    /**
     * Returns trades with a given stock for a given period of time.
     *
     * @param type      a type of trades to retrieve
     * @param stocks    stocks to retrieve trades for
     * @param from      the beginning of a period (inclusive) to retrieve trades for
     * @param to        the end of a period (exclusive) to retrieve trades for
     * @return      all the trades that meet the given conditions
     */
    Set<Trade> getByTypeAndStockAndPeriod(TradeType type, Collection<Stock> stocks,
                                          LocalDateTime from, LocalDateTime to);

}
