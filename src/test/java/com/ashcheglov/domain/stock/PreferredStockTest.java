package com.ashcheglov.domain.stock;

import org.junit.Test;

import java.math.BigDecimal;

import static com.ashcheglov.domain.stock.StockType.PREFERRED;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PreferredStockTest {

    private BigDecimal lastDividend = BigDecimal.valueOf(8);
    private BigDecimal fixedDividend = BigDecimal.valueOf(0.02);
    private BigDecimal parValue = BigDecimal.valueOf(100);

    BaseStock stock = new PreferredStock("GIN", lastDividend, fixedDividend, parValue);

    @Test
    public void shouldReturnPreferredType() {
        assertEquals(PREFERRED, stock.getType());
    }

    @Test
    public void shouldBeEqual() {
        BaseStock stock1 = new PreferredStock("ST1", valueOf(1), valueOf(11), valueOf(111));
        BaseStock stock2 = new PreferredStock("ST1", valueOf(2), valueOf(22), valueOf(222));
        assertEquals(stock1, stock2);
    }

    @Test
    public void shouldBeUnequal() {
        BaseStock stock1 = new PreferredStock("ST1", valueOf(1), valueOf(11), valueOf(111));
        BaseStock stock2 = new PreferredStock("ST2", valueOf(1), valueOf(11), valueOf(111));
        assertNotEquals(stock1, stock2);
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