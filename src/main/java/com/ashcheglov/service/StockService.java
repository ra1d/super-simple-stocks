package com.ashcheglov.service;

import java.math.BigDecimal;

/**
 * @author Anton
 * @since 17.09.2016
 */
public interface StockService {

    BigDecimal calculateDividendYield(String stockSymbol, BigDecimal price);

    BigDecimal calculatePERatio(String stockSymbol, BigDecimal price);

}
