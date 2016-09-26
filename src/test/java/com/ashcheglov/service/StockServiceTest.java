package com.ashcheglov.service;

import com.ashcheglov.dao.StockDao;
import com.ashcheglov.domain.stock.BaseStock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockServiceTest {

    @Mock
    private BaseStock stock;

    @Mock
    private StockDao stockDao;

    @InjectMocks
    private StockService stockService = new StockServiceImpl();

    private String stockSymbol = "POP";
    private BigDecimal price = BigDecimal.valueOf(50);

    @Before
    public void setUp() {
        // given
        when(stockDao.getBySymbol(stockSymbol)).thenReturn(stock);
    }

    @Test
    public void shouldCalculateDividendYield() {
        // when
        stockService.calculateDividendYield(stockSymbol, price);

        // then
        verify(stock).calculateDividendYield(price);
    }

    @Test
    public void shouldCalculatePERatio() {
        // when
        stockService.calculatePERatio(stockSymbol, price);

        // then
        verify(stock).calculatePERatio(price);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_NoSuchStock() {
        // given
        when(stockDao.getBySymbol(stockSymbol))
                .thenThrow(new IllegalArgumentException("Expected exception"));

        // when
        stockService.calculateDividendYield(stockSymbol, BigDecimal.valueOf(50));

        // then IllegalArgumentException should be thrown
    }

}