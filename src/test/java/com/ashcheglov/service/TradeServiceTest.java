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

import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;
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

    private TradeType tradeType = TradeType.BUY;
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