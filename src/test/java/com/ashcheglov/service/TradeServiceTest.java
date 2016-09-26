package com.ashcheglov.service;

import com.ashcheglov.dao.StockDao;
import com.ashcheglov.dao.TradeDao;
import com.ashcheglov.domain.stock.BaseStock;
import com.ashcheglov.domain.stock.Stock;
import com.ashcheglov.domain.trade.Trade;
import com.ashcheglov.domain.trade.TradeFactory;
import com.ashcheglov.domain.trade.TradeType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

import static com.ashcheglov.domain.trade.TradeType.BUY;
import static com.ashcheglov.service.TradeServiceImpl.SCALE;
import static com.ashcheglov.service.TradeServiceImpl.VOL_WEIGHTED_STOCK_PRICE_PERIOD;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TradeServiceTest {

    @Mock
    private TradeDao tradeDao;

    @Mock
    private StockDao stockDao;

    @Mock
    private Trade trade1;

    @Mock
    private Trade trade2;

    @Mock
    private Trade trade3;

    @Mock
    private Trade trade4;

    @Mock
    private Trade trade5;

    @Mock
    private BaseStock stock1;

    @Mock
    private BaseStock stock2;

    @Mock
    private TradeFactory tradeFactory;

    @InjectMocks
    private TradeService tradeService = new TradeServiceImpl();

    @Captor
    private ArgumentCaptor<Collection<Stock>> stockCaptor;

    @Captor
    private ArgumentCaptor<LocalDateTime> fromCaptor;

    @Captor
    private ArgumentCaptor<LocalDateTime> toCaptor;

    private TradeType tradeType = BUY;
    private String stockSymbol = "JOE";
    private long quantity = 1000L;
    private BigDecimal price = valueOf(50);

    private BigDecimal expectedVWSP1;
    private BigDecimal expectedVWSP2;
    private BigDecimal expectedASI;

    @Before
    public void setUp() {
        long trade1Qnty = 150L;
        BigDecimal trade1Price = valueOf(10);

        long trade2Qnty = 50L;
        BigDecimal trade2Price = valueOf(15);

        long trade3Qnty = 250L;
        BigDecimal trade3Price = valueOf(20);

        long trade4Qnty = 100L;
        BigDecimal trade4Price = valueOf(30);

        long trade5Qnty = 200L;
        BigDecimal trade5Price = valueOf(25);

        when(trade1.getQuantity()).thenReturn(trade1Qnty);
        when(trade1.getPrice()).thenReturn(trade1Price);
        when(trade1.getStock()).thenReturn(stock1);

        when(trade2.getQuantity()).thenReturn(trade2Qnty);
        when(trade2.getPrice()).thenReturn(trade2Price);
        when(trade2.getStock()).thenReturn(stock1);

        when(trade3.getQuantity()).thenReturn(trade3Qnty);
        when(trade3.getPrice()).thenReturn(trade3Price);
        when(trade3.getStock()).thenReturn(stock1);

        when(trade4.getQuantity()).thenReturn(trade4Qnty);
        when(trade4.getPrice()).thenReturn(trade4Price);
        when(trade4.getStock()).thenReturn(stock2);

        when(trade5.getQuantity()).thenReturn(trade5Qnty);
        when(trade5.getPrice()).thenReturn(trade5Price);
        when(trade5.getStock()).thenReturn(stock2);

        BigDecimal trade1TotalPrice = trade1Price.multiply(valueOf(trade1Qnty));
        BigDecimal trade2TotalPrice = trade2Price.multiply(valueOf(trade2Qnty));
        BigDecimal trade3TotalPrice = trade3Price.multiply(valueOf(trade3Qnty));
        BigDecimal trade4TotalPrice = trade4Price.multiply(valueOf(trade4Qnty));
        BigDecimal trade5TotalPrice = trade5Price.multiply(valueOf(trade5Qnty));

        BigDecimal totalTradedPrice1 =
                trade1TotalPrice.add(trade2TotalPrice).add(trade3TotalPrice);
        BigDecimal totalQuantity1 =
                valueOf(trade1Qnty).add(valueOf(trade2Qnty)).add(valueOf(trade3Qnty));

        BigDecimal totalTradedPrice2 = trade4TotalPrice.add(trade5TotalPrice);
        BigDecimal totalQuantity2 = valueOf(trade4Qnty).add(valueOf(trade5Qnty));

        expectedVWSP1 = totalTradedPrice1.divide(totalQuantity1, SCALE, ROUND_HALF_UP);
        expectedVWSP2 = totalTradedPrice2.divide(totalQuantity2, SCALE, ROUND_HALF_UP);

        expectedASI = valueOf(pow((expectedVWSP1.multiply(expectedVWSP2)).doubleValue(), 0.5));
    }

    @Test
    public void shouldRecordTrade() {
        // given
        when(stockDao.getBySymbol(stockSymbol)).thenReturn(stock1);
        when(tradeFactory.newInstance(tradeType, stock1, quantity, price)).thenReturn(trade1);

        // when
        Trade savedTrade = tradeService.recordTrade(tradeType, stockSymbol, quantity, price);

        // then
        verify(tradeDao).save(trade1);
        assertEquals(trade1, savedTrade);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotRecordTrade_ZeroQuantity() {
        // given
        quantity = 0;

        // when
        tradeService.recordTrade(tradeType, stockSymbol, quantity, price);

        // then IllegalArgumentException expected
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotRecordTrade_NegativeQuantity() {
        // given
        quantity = -1000;

        // when
        tradeService.recordTrade(tradeType, stockSymbol, quantity, price);

        // then IllegalArgumentException expected
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotRecordTrade_ZeroPrice() {
        // given
        price = ZERO;

        // when
        tradeService.recordTrade(tradeType, stockSymbol, quantity, price);

        // then IllegalArgumentException expected
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotRecordTrade_NegativePrice() {
        // given
        price = valueOf(-1L);

        // when
        tradeService.recordTrade(tradeType, stockSymbol, quantity, price);

        // then IllegalArgumentException expected
    }

    @Test
    public void shouldCalculateVolumeWeightedStockPrice() {
        // given
        LocalDateTime to = now();
        LocalDateTime from = to.minusMinutes(VOL_WEIGHTED_STOCK_PRICE_PERIOD);

        when(stockDao.getBySymbol(stockSymbol)).thenReturn(stock1);
        when(tradeDao.getByTypeAndStockAndPeriod(
                any(TradeType.class), any(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new HashSet<>(asList(trade1, trade2, trade3)));

        // when
        BigDecimal volWeightedStockPrice =
                tradeService.calculateVolumeWeightedStockPrice(stockSymbol);

        // then
        verify(tradeDao).getByTypeAndStockAndPeriod(
                eq(BUY), stockCaptor.capture(), fromCaptor.capture(), toCaptor.capture());

        Collection<Stock> capturedStocks = stockCaptor.getValue();
        assertEquals(1, capturedStocks.size());
        assertTrue(capturedStocks.contains(stock1));

        assertTrue(abs(MILLIS.between(from, fromCaptor.getValue())) < 50);
        assertTrue(abs(MILLIS.between(to, toCaptor.getValue())) < 50);

        assertEquals(expectedVWSP1, volWeightedStockPrice);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotCalculateVolumeWeightedStockPrice_NoTrades() {
        // given
        LocalDateTime to = now();
        LocalDateTime from = to.minusMinutes(VOL_WEIGHTED_STOCK_PRICE_PERIOD);

        when(stockDao.getBySymbol(stockSymbol)).thenReturn(stock1);
        when(tradeDao.getByTypeAndStockAndPeriod(
                any(TradeType.class), any(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(emptySet());

        // when
        tradeService.calculateVolumeWeightedStockPrice(stockSymbol);

        // then IllegalArgumentException expected
        verify(tradeDao).getByTypeAndStockAndPeriod(
                eq(BUY), stockCaptor.capture(), fromCaptor.capture(), toCaptor.capture());

        Collection<Stock> capturedStocks = stockCaptor.getValue();
        assertEquals(1, capturedStocks.size());
        assertTrue(capturedStocks.contains(stock1));

        assertTrue(abs(MILLIS.between(from, fromCaptor.getValue())) < 50);
        assertTrue(abs(MILLIS.between(to, toCaptor.getValue())) < 50);
    }

    @Test
    public void shouldCalculateAllShareIndex() {
        // given
        LocalDateTime to = now();
        LocalDateTime from = to.minusMinutes(VOL_WEIGHTED_STOCK_PRICE_PERIOD);

        when(stockDao.getAll()).thenReturn(new HashSet<>(asList(stock1, stock2)));
        when(tradeDao.getByTypeAndStockAndPeriod(
                any(TradeType.class), any(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(new HashSet<>(asList(trade1, trade2, trade3, trade4, trade5)));

        // when
        BigDecimal allShareIndex = tradeService.calculateAllShareIndex();

        // then
        verify(tradeDao).getByTypeAndStockAndPeriod(
                eq(BUY), stockCaptor.capture(),
                fromCaptor.capture(), toCaptor.capture());

        Collection<Stock> capturedStocks = stockCaptor.getValue();
        assertEquals(2, capturedStocks.size());
        assertTrue(capturedStocks.containsAll(asList(stock1, stock2)));

        assertTrue(abs(MILLIS.between(from, fromCaptor.getValue())) < 50);
        assertTrue(abs(MILLIS.between(to, toCaptor.getValue())) < 50);

        assertEquals(expectedASI, allShareIndex);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotCalculateAllShareIndex_NoTrades() {
        // given
        when(stockDao.getAll()).thenReturn(new HashSet<>(asList(stock1, stock2)));
        when(tradeDao.getByTypeAndStockAndPeriod(
                any(TradeType.class), any(), any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(emptySet());

        // when
        tradeService.calculateAllShareIndex();

        // then IllegalStateException expected
    }

}