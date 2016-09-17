package com.ashcheglov;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StockMarketTest {

    private StockMarket stockMarket = new StockMarket();

    @Test
    public void shouldReturnHello() {
        assertEquals("Hello from the Global Beverage Corporation Exchange!", stockMarket.start());
    }

}
