package com.ashcheglov.domain.stock;

import org.junit.Test;

import java.math.BigDecimal;

import static com.ashcheglov.domain.stock.StockType.COMMON;
import static org.junit.Assert.assertEquals;

public class CommonStockTest {

    private BigDecimal lastDividend = BigDecimal.valueOf(8);
    private BigDecimal parValue = BigDecimal.valueOf(100);

    private CommonStock stock = new CommonStock("POP", lastDividend, parValue);

    @Test
    public void shouldReturnCommonType() {
        assertEquals(COMMON, stock.getType());
    }

    @Test
    public void shouldCalculateDividendYield() {
        // given
        BigDecimal price = BigDecimal.valueOf(50);

        // when
        BigDecimal dividendYield = stock.calculateDividendYield(price);

        // then
        assertEquals(lastDividend.divide(price), dividendYield);
    }

    @Test
    public void shouldCalculatePERatio() {
        // given
        BigDecimal price = BigDecimal.valueOf(50);

        // when
        BigDecimal peRatio = stock.calculatePERatio(price);

        // then
        assertEquals(price.divide(lastDividend), peRatio);
    }

}