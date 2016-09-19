package com.ashcheglov.dao;

import com.ashcheglov.domain.stock.Stock;
import com.ashcheglov.domain.trade.Trade;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Map;

import static com.ashcheglov.domain.trade.TradeType.BUY;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This test is somewhat far-fetched due to the lack of any database
 * (as per the task specification).
 */
public class TradeDaoTest {

    @Mock
    private Stock stock;

    private TradeDao tradeDao = new TradeDaoImpl();

    @Test
    public void shouldSave_EmptyStorage() {
        // given
        long quantity = 100;
        BigDecimal price = valueOf(75);
        Trade trade = new Trade(BUY, stock, quantity, price, now());

        // when
        tradeDao.save(trade);

        // then
        Map<Long, Trade> allTrades = tradeDao.getAll();
        assertEquals(1, allTrades.size());
        assertTrue(allTrades.containsValue(trade));
    }

    @Test
    public void shouldSave_NonEmptyStorage() {
        // given
        long quantity = 100;
        BigDecimal price = valueOf(75);
        Trade trade1 = new Trade(BUY, stock, quantity, price, now());
        Trade trade2 = new Trade(BUY, stock, quantity, price, now());

        // when
        tradeDao.save(trade1);
        tradeDao.save(trade2);

        // then
        Map<Long, Trade> allTrades = tradeDao.getAll();
        assertEquals(2, allTrades.size());
        assertTrue(allTrades.values().containsAll(asList(trade1, trade2)));
    }

}