package com.ashcheglov.domain.trade;

import com.ashcheglov.domain.stock.Stock;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.ashcheglov.domain.trade.TradeType.BUY;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;
import static org.junit.Assert.*;

/**
 * This test appears to be of integration nature.
 */
public class TradeFactoryTest {

    @Mock
    private Stock stock;

    private TradeFactory tradeFactory = new TradeFactory();

    @Test
    public void shouldCreateNewTrade() {
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

}