package com.ashcheglov.domain.trade;

import com.ashcheglov.domain.stock.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.ashcheglov.domain.trade.TradeType.BUY;
import static com.ashcheglov.domain.trade.TradeType.SELL;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This test appears to be of integration nature.
 */
@RunWith(MockitoJUnitRunner.class)
public class TradeFactoryTest {

    @Mock
    private Stock stock;

    private TradeFactory tradeFactory = new TradeFactory();

    @Test
    public void shouldCreateNewTrade_BuyType() {
        // given
        long quantity = 100;
        BigDecimal price = valueOf(50);

        // when
        Trade trade = tradeFactory.newInstance(BUY, stock, quantity, price);

        // then
        assertEquals(BUY, trade.getType());
        assertEquals(stock, trade.getStock());
        assertEquals(quantity, trade.getQuantity());
        assertEquals(price, trade.getPrice());

        LocalDateTime tradeTimeStamp = trade.getTimeStamp();
        assertNotNull(tradeTimeStamp);
        assertTrue(tradeTimeStamp.until(now(), MILLIS) < 1000);
    }

    @Test
    public void shouldCreate2TradesWithDiffTimestamp_SellType() throws InterruptedException {
        // given
        long quantity = 100;
        BigDecimal price = valueOf(50);

        // when
        Trade trade1 = tradeFactory.newInstance(SELL, stock, quantity, price);
        Thread.sleep(100L);
        Trade trade2 = tradeFactory.newInstance(SELL, stock, quantity, price);

        // then
        assertEquals(SELL, trade1.getType());
        assertEquals(stock, trade1.getStock());
        assertEquals(quantity, trade1.getQuantity());
        assertEquals(price, trade1.getPrice());

        LocalDateTime trade1TimeStamp = trade1.getTimeStamp();
        assertNotNull(trade1TimeStamp);
        LocalDateTime trade2TimeStamp = trade2.getTimeStamp();
        assertNotNull(trade2TimeStamp);
        assertTrue(trade1TimeStamp.until(trade2TimeStamp, MILLIS) >= 100);
    }

}