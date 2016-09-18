package com.ashcheglov.dao;

import com.ashcheglov.domain.trade.Trade;

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

}
