package com.ashcheglov.domain.stock;

import org.junit.Test;

import java.math.BigDecimal;

import static com.ashcheglov.domain.stock.StockType.COMMON;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CommonStockTest {

    private BigDecimal lastDividend = valueOf(8);
    private BigDecimal parValue = valueOf(100);

    private BaseStock stock = new CommonStock("POP", lastDividend, parValue);

    @Test
    public void shouldReturnCommonType() {
        assertEquals(COMMON, stock.getType());
    }

    @Test
    public void shouldBeEqual() {
        BaseStock stock1 = new CommonStock("ST1", valueOf(1), valueOf(11));
        BaseStock stock2 = new CommonStock("ST1", valueOf(2), valueOf(22));
        assertEquals(stock1, stock2);
    }

    @Test
    public void shouldBeUnequal() {
        BaseStock stock1 = new CommonStock("ST1", valueOf(1), valueOf(11));
        BaseStock stock2 = new CommonStock("ST2", valueOf(1), valueOf(11));
        assertNotEquals(stock1, stock2);
    }

    @Test
    public void shouldCalculateDividendYield() {
        // given
        BigDecimal price = valueOf(50);

        // when
        BigDecimal dividendYield = stock.calculateDividendYield(price);

        // then
        assertEquals(lastDividend.divide(price), dividendYield);
    }

    @Test
    public void shouldCalculatePERatio() {
        // given
        BigDecimal price = valueOf(50);

        // when
        BigDecimal peRatio = stock.calculatePERatio(price);

        // then
        assertEquals(price.divide(lastDividend), peRatio);
    }

}