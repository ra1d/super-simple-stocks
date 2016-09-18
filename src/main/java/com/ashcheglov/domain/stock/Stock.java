package com.ashcheglov.domain.stock;

import java.math.BigDecimal;

/**
 * @author Anton
 * @since 18.09.2016
 */
public interface Stock {

    BigDecimal calculateDividendYield(BigDecimal price);

    BigDecimal calculatePERatio(BigDecimal price);

}
