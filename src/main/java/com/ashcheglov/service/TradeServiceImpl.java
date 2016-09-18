package com.ashcheglov.service;

import com.ashcheglov.dao.StockDao;
import com.ashcheglov.dao.TradeDao;
import com.ashcheglov.domain.stock.Stock;
import com.ashcheglov.domain.trade.Trade;
import com.ashcheglov.domain.trade.TradeFactory;
import com.ashcheglov.domain.trade.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Anton
 * @since 18.09.2016
 */
@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeDao tradeDao;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private TradeFactory tradeFactory;

    @Override
    public Trade recordTrade(TradeType tradeType, String stockSymbol,
                             long quantity, BigDecimal price) {
        Trade trade;
        if (quantity > 0) {
            Stock stock = stockDao.getBySymbol(stockSymbol);
            trade = tradeFactory.newInstance(tradeType, stock, quantity, price);
            tradeDao.save(trade);
        } else {
            throw new IllegalArgumentException("Invalid quantity! Expected: > 1, actual: " + quantity);
        }
        return trade;
    }

}
