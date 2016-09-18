package com.ashcheglov.dao;

import com.ashcheglov.domain.stock.Stock;

/**
 * @author Anton
 * @since 18.09.2016
 */
public interface StockDao {

    /**
     * Finds a stock by its symbol
     *
     * @param stockSymbol   a stock symbol
     * @return              a stock with the given symbol
     */
    Stock getBySymbol(String stockSymbol);

}
