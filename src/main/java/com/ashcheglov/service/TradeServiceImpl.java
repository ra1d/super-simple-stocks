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
import java.time.LocalDateTime;
import java.util.Set;

import static com.ashcheglov.domain.trade.TradeType.BUY;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

/**
 * @author Anton
 * @since 18.09.2016
 */
@Service
public class TradeServiceImpl implements TradeService {

    /**
     * @see java.math.BigDecimal#scale
     */
    static final int SCALE = 10;

    @Autowired
    private TradeDao tradeDao;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private TradeFactory tradeFactory;

    @Override
    public Trade recordTrade(TradeType type, String stockSymbol,
                             long quantity, BigDecimal price) {
        Trade trade;
        if (quantity > 0) {
            Stock stock = stockDao.getBySymbol(stockSymbol);
            trade = tradeFactory.newInstance(type, stock, quantity, price);
            tradeDao.save(trade);
        } else {
            throw new IllegalArgumentException("Invalid quantity! Expected: > 1, actual: " + quantity);
        }
        return trade;
    }

    @Override
    public BigDecimal calculateVolumeWeightedStockPrice(String stockSymbol,
                                                        LocalDateTime from, LocalDateTime to) {
        Stock stock = stockDao.getBySymbol(stockSymbol);
        Set<Trade> buyTrades = tradeDao.getByTypeAndStockAndPeriod(BUY, stock, from, to);

        BigDecimal tradedPricesTotal = buyTrades.stream()
                .map(trade -> {
                    BigDecimal price = trade.getPrice();
                    BigDecimal quantity = valueOf(trade.getQuantity());
                    return (price.multiply(quantity));
                })
                .reduce(ZERO, BigDecimal::add);

        BigDecimal totalQuantity = valueOf(buyTrades.stream()
                .mapToLong(Trade::getQuantity).sum());

        return tradedPricesTotal.divide(totalQuantity, SCALE, ROUND_HALF_UP);
    }

}
