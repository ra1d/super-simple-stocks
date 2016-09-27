package com.ashcheglov.service;

import com.ashcheglov.domain.trade.Trade;
import com.ashcheglov.domain.trade.TradeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * A facade aggregating the GBCE API specified in the task description.
 *
 * @author Anton
 * @since 27.09.2016
 */
@Service
public class GBCEFacade {

    @Autowired
    private StockService stockService;

    @Autowired
    private TradeService tradeService;

    public BigDecimal calculateDividendYield(String stockSymbol, BigDecimal price) {
        return stockService.calculateDividendYield(stockSymbol, price);
    }

    public BigDecimal calculatePERatio(String stockSymbol, BigDecimal price) {
        return stockService.calculatePERatio(stockSymbol, price);
    }

    public Trade recordTrade(TradeType type, String stockSymbol, long quantity, BigDecimal price) {
        return tradeService.recordTrade(type, stockSymbol, quantity, price);
    }

    public BigDecimal calculateVolumeWeightedStockPrice(String stockSymbol) {
        return tradeService.calculateVolumeWeightedStockPrice(stockSymbol);
    }

    public BigDecimal calculateAllShareIndex() {
        return tradeService.calculateAllShareIndex();
    }

}
