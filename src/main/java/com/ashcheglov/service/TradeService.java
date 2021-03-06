package com.ashcheglov.service;

import com.ashcheglov.domain.trade.Trade;
import com.ashcheglov.domain.trade.TradeType;

import java.math.BigDecimal;

/**
 * Assumptions:
 * - the price mentioned in the paragraph 2.a.iii of the task
 *   is per one stock;
 * - for the purpose of Volume Weighted Stock Price calculation,
 *   only bought stocks are taken into account
 *   @see TradeType#BUY;
 *
 * @author Anton
 * @since 18.09.2016
 */
public interface TradeService {

    /**
     * Records a trade, i.e. buys or sells a given quantity of stocks
     * of some type at a given price.
     *
     * @param type          a type of the operation
     * @param stockSymbol   a stock to perform the operation on
     * @param quantity      a quantity of stocks
     * @param price         a price per stock
     * @return              a trade record created as a result of the operation
     */
    Trade recordTrade(TradeType type, String stockSymbol,
                      long quantity, BigDecimal price);

    /**
     * Calculates Volume Weighted Stock Price of a stock
     * based on trades in past 5 minutes.
     *
     * @param stockSymbol   a stock to perform the operation on
     * @return              Volume Weighted Stock Price
     */
    BigDecimal calculateVolumeWeightedStockPrice(String stockSymbol);

    /**
     * Calculates the GBCE All Share Index based on trades
     * in past 5 minutes for all the existing stocks.
     *
     * @return All Share Index
     */
    BigDecimal calculateAllShareIndex();

}
