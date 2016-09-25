package com.ashcheglov.dao;

import com.ashcheglov.domain.stock.Stock;
import com.ashcheglov.domain.trade.Trade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static com.ashcheglov.dao.TradeDaoImpl.tradesStorage;
import static com.ashcheglov.domain.trade.TradeType.BUY;
import static com.ashcheglov.domain.trade.TradeType.SELL;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

/**
 * This test is somewhat far-fetched due to the lack of any database
 * (as per the task specification).
 */
@RunWith(MockitoJUnitRunner.class)
public class TradeDaoTest {

    @Mock
    private Stock stock1;

    @Mock
    private Stock stock2;

    private TradeDao tradeDao = new TradeDaoImpl();

    @Before
    public void setUp() {
        tradesStorage.clear();
    }

    @Test
    public void shouldSaveToStorage() {
        // given
        long quantity = 100;
        BigDecimal price = valueOf(75);
        Trade trade1 = new Trade(1L, BUY, stock1, quantity, price, now());
        Trade trade2 = new Trade(2L, BUY, stock1, quantity, price, now());

        assumeTrue("Stale data found", tradesStorage.isEmpty());

        // when
        assertTrue(tradeDao.save(trade1));
        assertTrue(tradeDao.save(trade2));

        // then
        assertEquals(2, tradesStorage.size());
        assertTrue(tradesStorage.contains(trade1));
        assertTrue(tradesStorage.contains(trade2));
    }

    @Test
    public void shouldFilterOutByType() {
        // given
        Trade trade1 = new Trade(
                1L, BUY, stock1, 110L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        Trade trade2 = new Trade(
                2L, SELL, stock1, 120L, valueOf(20), LocalDateTime.of(2011, 1, 1, 2, 2, 2));
        Trade trade3 = new Trade(
                3L, BUY, stock1, 130L, valueOf(30), LocalDateTime.of(2011, 1, 1, 3, 3, 3));
        tradesStorage.addAll(asList(trade1, trade2, trade3));

        assumeTrue("Test data not ready", tradesStorage.size() == 3);

        // when
        Set<Trade> trades = tradeDao.getByTypeAndStockAndPeriod(
                BUY, singleton(stock1),
                LocalDateTime.of(2011, 1, 1, 0, 0, 0), LocalDateTime.of(2011, 1, 1, 4, 4, 4)
        );

        // then
        assertEquals(2, trades.size());
        assertTrue(trades.containsAll(asList(trade1, trade3)));
    }

    @Test
    public void shouldFilterOutByStock() {
        // given
        Trade trade1 = new Trade(
                1L, BUY, stock1, 110L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        Trade trade2 = new Trade(
                2L, BUY, stock2, 120L, valueOf(20), LocalDateTime.of(2011, 1, 1, 2, 2, 2));
        Trade trade3 = new Trade(
                3L, BUY, stock1, 130L, valueOf(30), LocalDateTime.of(2011, 1, 1, 3, 3, 3));
        tradesStorage.addAll(asList(trade1, trade2, trade3));

        assumeTrue("Test data not ready", tradesStorage.size() == 3);

        // when
        Set<Trade> trades = tradeDao.getByTypeAndStockAndPeriod(
                BUY, singleton(stock1),
                LocalDateTime.of(2011, 1, 1, 0, 0, 0), LocalDateTime.of(2011, 1, 1, 4, 4, 4)
        );

        // then
        assertEquals(2, trades.size());
        assertTrue(trades.containsAll(asList(trade1, trade3)));
    }

    @Test
    public void shouldFilterOutByPeriod() {
        // given
        Trade trade1 = new Trade(
                1L, BUY, stock1, 110L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        Trade trade2 = new Trade(
                2L, BUY, stock1, 120L, valueOf(20), LocalDateTime.of(2011, 1, 1, 2, 2, 2));
        Trade trade3 = new Trade(
                3L, BUY, stock1, 130L, valueOf(30), LocalDateTime.of(2011, 1, 1, 3, 3, 3));
        tradesStorage.addAll(asList(trade1, trade2, trade3));

        assumeTrue("Test data not ready", tradesStorage.size() == 3);

        // when
        Set<Trade> trades = tradeDao.getByTypeAndStockAndPeriod(
                BUY, singleton(stock1),
                LocalDateTime.of(2011, 1, 1, 1, 1, 1), LocalDateTime.of(2011, 1, 1, 3, 3, 3)
        );

        // then
        assertEquals(2, trades.size());
        assertTrue(trades.containsAll(asList(trade1, trade2)));
    }

    @Test
    public void shouldGetFor2Stocks_NoFiltering() {
        // given
        Trade trade1 = new Trade(
                1L, BUY, stock1, 110L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        Trade trade2 = new Trade(
                2L, BUY, stock2, 120L, valueOf(20), LocalDateTime.of(2011, 1, 1, 2, 2, 2));
        Trade trade3 = new Trade(
                3L, BUY, stock1, 130L, valueOf(30), LocalDateTime.of(2011, 1, 1, 3, 3, 3));

        tradesStorage.addAll(asList(trade1, trade2, trade3));

        assumeTrue("Test data not ready", tradesStorage.size() == 3);

        // when
        Set<Trade> trades = tradeDao.getByTypeAndStockAndPeriod(
                BUY, asList(stock1, stock2),
                LocalDateTime.of(2011, 1, 1, 1, 1, 1), LocalDateTime.of(2011, 1, 1, 3, 3, 4)
        );

        // then
        assertEquals(3, trades.size());
        assertTrue(trades.containsAll(asList(trade1, trade2, trade3)));
    }

}