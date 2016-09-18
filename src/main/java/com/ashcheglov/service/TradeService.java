package com.ashcheglov.service;

import com.ashcheglov.domain.trade.Trade;
import com.ashcheglov.domain.trade.TradeType;

import java.math.BigDecimal;

/**
 * Assumptions:
 * the price mentioned in the paragraph 2.a.iii of the task is per one stock;
 *
 * @author Anton
 * @since 18.09.2016
 */
public interface TradeService {

    /**
     * Records a trade, i.e. an operation with a given quantity of stocks.
     *
     * @param tradeType     a type of the operation
     * @param stockSymbol   a stock to perform the operation on
     * @param quantity      a quantity of stocks
     * @param price         a price per stock
     * @return              a trade record created as a result of the operation
     */
    Trade recordTrade(TradeType tradeType, String stockSymbol,
                      long quantity, BigDecimal price);

}
