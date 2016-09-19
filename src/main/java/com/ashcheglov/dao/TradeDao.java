package com.ashcheglov.dao;

import com.ashcheglov.domain.trade.Trade;

import java.util.Map;

/**
 * @author Anton
 * @since 18.09.2016
 */
public interface TradeDao {

    /**
     * Saves a trade, i.e. an operation with a given quantity of stocks.
     *
     * @param trade a trade record to save
     */
    void save(Trade trade);

    /**
     * Returns all the recorded trades along with their IDs.
     *
     * @return all the recorded trades
     */
    Map<Long, Trade> getAll();

}
