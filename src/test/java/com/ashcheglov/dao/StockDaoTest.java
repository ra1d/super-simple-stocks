package com.ashcheglov.dao;

import com.ashcheglov.domain.stock.BaseStock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static com.ashcheglov.dao.StockDaoImpl.stocksStorage;
import static com.ashcheglov.domain.stock.StockType.PREFERRED;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockDaoTest {

    @Mock
    private BaseStock stock1;

    @Mock
    private BaseStock stock2;

    private StockDao stockDao = new StockDaoImpl();

    @Before
    public void setUp() {
        stocksStorage.clear();
    }

    @Test
    public void shouldGetBySymbol() {
        // given
        String symbol = "TEST";
        when(stock1.getSymbol()).thenReturn(symbol);
        when(stock1.getType()).thenReturn(PREFERRED);
        stocksStorage.add(stock1);

        // when
        BaseStock stock = stockDao.getBySymbol(symbol);

        // then
        assertEquals(symbol, stock.getSymbol());
        assertEquals(PREFERRED, stock.getType());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGetBySymbol_NoSuchStock() {
        // given
        String symbol = "TEST";

        // when
        BaseStock stock = stockDao.getBySymbol(symbol);

        // then IllegalArgumentException expected
    }

    @Test
    public void shouldGetAll() {
        // given
        stocksStorage.add(stock1);
        stocksStorage.add(stock2);

        // when
        Set<BaseStock> stocks = stockDao.getAll();

        // then
        assertEquals(2, stocks.size());
        assertTrue(stocks.containsAll(asList(stock1, stock2)));
    }

}