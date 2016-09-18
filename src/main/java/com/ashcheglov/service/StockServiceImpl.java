package com.ashcheglov.service;

import com.ashcheglov.dao.StockDao;
import com.ashcheglov.domain.stock.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Anton
 * @since 17.09.2016
 */
@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Override
    public BigDecimal calculateDividendYield(String stockSymbol, BigDecimal price) {
        Stock stock = stockDao.getBySymbol(stockSymbol);
        return stock.calculateDividendYield(price);
    }

    @Override
    public BigDecimal calculatePERatio(String stockSymbol, BigDecimal price) {
        Stock stock = stockDao.getBySymbol(stockSymbol);
        return stock.calculatePERatio(price);
    }

}
