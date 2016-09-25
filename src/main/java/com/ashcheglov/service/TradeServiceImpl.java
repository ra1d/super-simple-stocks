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
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static com.ashcheglov.domain.trade.TradeType.BUY;
import static java.lang.Math.pow;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.time.LocalDateTime.now;
import static java.util.Collections.singleton;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toSet;

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

    /**
     * A time horizon to calculate Volume Weighted Stock Price for (in minutes)
     */
    static final int VOL_WEIGHTED_STOCK_PRICE_PERIOD = 5;

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
        if (quantity > 0 && price.compareTo(ZERO) > 0) {
            Stock stock = stockDao.getBySymbol(stockSymbol);
            trade = tradeFactory.newInstance(type, stock, quantity, price);
            tradeDao.save(trade);
        } else {
            throw new IllegalArgumentException(String.format(
                    "Non-positive quantity or price! Quantity=[%s], price=[%s]",
                    quantity, price));
        }
        return trade;
    }

    @Override
    public BigDecimal calculateVolumeWeightedStockPrice(String stockSymbol) {
        Stock stock = stockDao.getBySymbol(stockSymbol);
        LocalDateTime to = now();
        LocalDateTime from = to.minusMinutes(VOL_WEIGHTED_STOCK_PRICE_PERIOD);
        Set<Trade> buyTrades = tradeDao.getByTypeAndStockAndPeriod(BUY, singleton(stock), from, to);
        return calculateVolumeWeightedStockPrice(buyTrades);
    }

    @Override
    public BigDecimal calculateAllShareIndex() {
        Set<Stock> stocks = stockDao.getAll();
        LocalDateTime to = now();
        LocalDateTime from = to.minusMinutes(VOL_WEIGHTED_STOCK_PRICE_PERIOD);

        Map<Stock, Set<Trade>> buyTradesByStock =
                tradeDao.getByTypeAndStockAndPeriod(BUY, stocks, from, to).stream()
                .collect(groupingBy(Trade::getStock, toSet()));

        double volWeightStockPriceProduct = buyTradesByStock.values().stream()
                .map(TradeServiceImpl::calculateVolumeWeightedStockPrice)
                .reduce(BigDecimal::multiply)
                .orElseThrow(() -> new IllegalStateException(String.format(
                        "No trades meet the given criteria! From=[%s], to=[%s], stocks=[%s]",
                        from, to,
                        stocks.stream().map(Stock::toString).collect(joining(", "))
                )))
                .doubleValue();

        return valueOf(pow(volWeightStockPriceProduct, (1.0 / buyTradesByStock.size())));
    }

    private static BigDecimal calculateVolumeWeightedStockPrice(Collection<Trade> trades) {
        BigDecimal tradedPricesTotal = trades.stream()
                .map(trade -> {
                    BigDecimal price = trade.getPrice();
                    BigDecimal quantity = valueOf(trade.getQuantity());
                    return (price.multiply(quantity));
                })
                .reduce(BigDecimal::add)
                .orElseThrow(() -> new IllegalArgumentException("No trades were provided!"));

        BigDecimal totalQuantity = valueOf(trades.stream()
                .mapToLong(Trade::getQuantity).sum());

        return tradedPricesTotal.divide(totalQuantity, SCALE, ROUND_HALF_UP);
    }

}
