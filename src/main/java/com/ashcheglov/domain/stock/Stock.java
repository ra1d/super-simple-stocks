package com.ashcheglov.domain.stock;

import java.math.BigDecimal;

/**
 * An interface that describes some basic operations common for all stocks.
 * Assumptions:
 * - for the purpose of P/E Ratio calculation, "Dividend" means
 *   "Last Dividend" for common stocks
 *   and ("Fixed Dividend" * "Par Value") for preferred stocks.
 *
 * @author Anton
 * @since 18.09.2016
 */
public interface Stock {

    /**
     * Calculates Dividend Yield of this stock for a given price
     *
     * @param price a price to calculate for
     * @return      Dividend Yield of a stock
     */
    BigDecimal calculateDividendYield(BigDecimal price);

    /**
     * Calculates P/E Ratio of this stock for a given price
     *
     * @param price a price to calculate for
     * @return      P/E Ratio of a stock
     */
    BigDecimal calculatePERatio(BigDecimal price);

}
