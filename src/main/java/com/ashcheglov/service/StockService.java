package com.ashcheglov.service;

import java.math.BigDecimal;

/**
 * Assumptions:
 * - a stock symbol is unique and can be used as an ID;
 * - stocks are passed by their IDs because:
 *      stock entities might be too heavy to toss them around;
 *      as long as this application is not a complete solution
 *      but rather a part of an API, it might be inconvenient
 *      to obtain a reference to a particular stock instance
 *      to pass it into a method.
 *
 * @author Anton
 * @since 17.09.2016
 */
public interface StockService {

    /**
     * Calculates Dividend Yield of a given stock for a given price
     *
     * @param stockSymbol   a stock to perform the operation on
     * @param price         a price to calculate for
     * @return              Dividend Yield of a stock
     */
    BigDecimal calculateDividendYield(String stockSymbol, BigDecimal price);

    /**
     * Calculates P/E Ratio of a given stock for a given price
     *
     * @param stockSymbol   a stock to perform the operation on
     * @param price         a price to calculate for
     * @return              P/E Ratio of a stock
     */
    BigDecimal calculatePERatio(String stockSymbol, BigDecimal price);

}
