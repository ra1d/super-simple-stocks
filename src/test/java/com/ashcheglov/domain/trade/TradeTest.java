package com.ashcheglov.domain.trade;

import com.ashcheglov.domain.stock.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static com.ashcheglov.domain.trade.TradeType.BUY;
import static com.ashcheglov.domain.trade.TradeType.SELL;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TradeTest {

    @Mock
    private Stock stock1;

    @Mock
    private Stock stock2;

    @Test
    public void shouldBeEqual() {
        Trade trade1 = new Trade(1L, BUY, stock1, 100L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        Trade trade2 = new Trade(1L, SELL, stock2, 200L, valueOf(20), LocalDateTime.of(2012, 2, 2, 2, 2, 2));
        assertEquals(trade1, trade2);
    }

    @Test
    public void shouldBeUnequal() {
        Trade trade1 = new Trade(1L, BUY, stock1, 100L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        Trade trade2 = new Trade(2L, BUY, stock1, 100L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        assertNotEquals(trade1, trade2);
    }

    @Test
    public void shouldBeLess_NaturalOrder() {
        Trade trade1 = new Trade(1L, BUY, stock1, 100L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        Trade trade2 = new Trade(2L, BUY, stock1, 100L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        assertTrue(trade1.compareTo(trade2) < 0);
    }

    @Test
    public void shouldBeGreater_NaturalOrder() {
        Trade trade1 = new Trade(3L, BUY, stock1, 100L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        Trade trade2 = new Trade(2L, BUY, stock1, 100L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        assertTrue(trade1.compareTo(trade2) > 0);
    }

    @Test
    public void shouldBeEqual_NaturalOrder() {
        Trade trade1 = new Trade(3L, BUY, stock1, 100L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        Trade trade2 = new Trade(3L, BUY, stock1, 100L, valueOf(10), LocalDateTime.of(2011, 1, 1, 1, 1, 1));
        assertTrue(trade1.compareTo(trade2) == 0);
    }


}