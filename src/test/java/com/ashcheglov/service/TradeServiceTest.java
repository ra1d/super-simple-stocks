package com.ashcheglov.service;

import com.ashcheglov.dao.StockDao;
import com.ashcheglov.dao.TradeDao;
import com.ashcheglov.domain.stock.CommonStock;
import com.ashcheglov.domain.stock.Stock;
import com.ashcheglov.domain.trade.Trade;
import com.ashcheglov.domain.trade.TradeFactory;
import com.ashcheglov.domain.trade.TradeType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.ashcheglov.domain.trade.TradeType.BUY;
import static com.ashcheglov.service.TradeServiceImpl.SCALE;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {

    @Mock
    private TradeDao tradeDao;

    @Mock
    private StockDao stockDao;

    @Mock
    private Trade trade;

    @Mock
    private TradeFactory tradeFactory;

    @InjectMocks
    private TradeService tradeService = new TradeServiceImpl();

    private TradeType tradeType = BUY;
    private String stockSymbol = "JOE";
    private BigDecimal price = valueOf(50);

    private Stock stock = new CommonStock(stockSymbol, valueOf(13), valueOf(250));

    @Test
    public void shouldRecordTrade() {
        // given
        long quantity = 1000;

        when(stockDao.getBySymbol(stockSymbol)).thenReturn(stock);
        when(tradeFactory.newInstance(tradeType, stock, quantity, price)).thenReturn(trade);

        // when
        Trade savedTrade = tradeService.recordTrade(tradeType, stockSymbol, quantity, price);

        // then
        verify(tradeDao).save(trade);
        assertEquals(trade, savedTrade);
    }

    @Test
    public void shouldCalculateVolumeWeightedStockPrice() {
        // give
        LocalDateTime to = now();
        LocalDateTime from = to.minusMinutes(5);

        long trade1Qnty = 150L;
        BigDecimal trade1Price = valueOf(10);
        long trade2Qnty = 50L;
        BigDecimal trade2Price = valueOf(15);
        long trade3Qnty = 250L;
        BigDecimal trade3Price = valueOf(20);

        Trade trade1 = mock(Trade.class);
        when(trade1.getQuantity()).thenReturn(trade1Qnty);
        when(trade1.getPrice()).thenReturn(trade1Price);

        Trade trade2 = mock(Trade.class);
        when(trade2.getQuantity()).thenReturn(trade2Qnty);
        when(trade2.getPrice()).thenReturn(trade2Price);

        Trade trade3 = mock(Trade.class);
        when(trade3.getQuantity()).thenReturn(trade3Qnty);
        when(trade3.getPrice()).thenReturn(trade3Price);

        Set<Trade> trades = new HashSet<>(asList(trade1, trade2, trade3));

        when(tradeDao.getByTypeAndStockAndPeriod(BUY, stock, from, to)).thenReturn(trades);
        when(stockDao.getBySymbol(stockSymbol)).thenReturn(stock);

        BigDecimal trade1TotalPrice = trade1Price.multiply(valueOf(trade1Qnty));
        BigDecimal trade2TotalPrice = trade2Price.multiply(valueOf(trade2Qnty));
        BigDecimal trade3TotalPrice = trade3Price.multiply(valueOf(trade3Qnty));

        BigDecimal totalTradedPrice = trade1TotalPrice.add(trade2TotalPrice).add(trade3TotalPrice);
        BigDecimal totalQuantity = valueOf(trade1Qnty).add(valueOf(trade2Qnty)).add(valueOf(trade3Qnty));

        BigDecimal expectedVWSP = totalTradedPrice.divide(totalQuantity, SCALE, ROUND_HALF_UP);

        // when
        BigDecimal volWeightedStockPrice =
                tradeService.calculateVolumeWeightedStockPrice(stockSymbol, from, to);

        // then
       assertEquals(expectedVWSP, volWeightedStockPrice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_ZeroQuantity() {
        // given
        long quantity = 0;

        // when
        tradeService.recordTrade(tradeType, stockSymbol, quantity, price);

        // then IllegalArgumentException expected
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowException_NegativeQuantity() {
        // given
        long quantity = -1000;

        // when
        tradeService.recordTrade(tradeType, stockSymbol, quantity, price);

        // then IllegalArgumentException expected
    }

}