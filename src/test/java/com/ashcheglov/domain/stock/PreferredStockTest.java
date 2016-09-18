package com.ashcheglov.domain.stock;

import org.junit.Test;

import java.math.BigDecimal;

import static com.ashcheglov.domain.stock.StockType.PREFERRED;
import static org.junit.Assert.assertEquals;

public class PreferredStockTest {

    private BigDecimal lastDividend = BigDecimal.valueOf(8);
    private BigDecimal fixedDividend = BigDecimal.valueOf(0.02);
    private BigDecimal parValue = BigDecimal.valueOf(100);

    PreferredStock stock = new PreferredStock("GIN", lastDividend, fixedDividend, parValue);

    @Test
    public void shouldReturnPreferredType() {
        assertEquals(PREFERRED, stock.getType());
    }

    @Test
    public void shouldCalculateDividendYield() {
        // given
        BigDecimal price = BigDecimal.valueOf(50);

        // when
        BigDecimal dividendYield = stock.calculateDividendYield(price);

        // then
        assertEquals((fixedDividend.multiply(parValue)).divide(price), dividendYield);
    }

    @Test
    public void shouldCalculatePERatio() {
        // given
        BigDecimal price = BigDecimal.valueOf(50);

        // when
        BigDecimal peRatio = stock.calculatePERatio(price);

        // then
        assertEquals(price.divide(fixedDividend.multiply(parValue)), peRatio);
    }

}