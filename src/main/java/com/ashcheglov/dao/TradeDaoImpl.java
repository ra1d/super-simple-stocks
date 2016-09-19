package com.ashcheglov.dao;

import com.ashcheglov.domain.trade.Trade;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Anton
 * @since 20.09.2016
 */
public class TradeDaoImpl implements TradeDao {

    private Map<Long, Trade> tradesStorage = new TreeMap<>();

    AtomicLong idGenerator = new AtomicLong();

    @Override
    public void save(Trade trade) {
        tradesStorage.put(idGenerator.incrementAndGet(), trade);
    }

    @Override
    public Map<Long, Trade> getAll() {
        return tradesStorage;
    }

}
