package com.ashcheglov.dao;

import com.ashcheglov.domain.stock.Stock;
import com.ashcheglov.domain.trade.Trade;
import com.ashcheglov.domain.trade.TradeType;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Collections.synchronizedSortedSet;
import static java.util.stream.Collectors.toSet;

/**
 * @author Anton
 * @since 20.09.2016
 */
public class TradeDaoImpl implements TradeDao {

    /**
     * A database emulation
     */
    static SortedSet<Trade> tradesStorage = synchronizedSortedSet(new TreeSet<>());

    @Override
    public boolean save(Trade trade) {
        return tradesStorage.add(trade);
    }

    @Override
    public Set<Trade> getByTypeAndStockAndPeriod(TradeType type, Collection<Stock> stocks,
                                                 LocalDateTime from, LocalDateTime to) {
        return tradesStorage.stream()
                .filter(trade -> type.equals(trade.getType()))
                .filter(trade -> stocks.contains(trade.getStock()))
                .filter(trade -> !trade.getTimeStamp().isBefore(from)
                        && trade.getTimeStamp().isBefore(to))
                .collect(toSet());
    }

}
