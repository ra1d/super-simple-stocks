package com.ashcheglov.dao;

import com.ashcheglov.domain.stock.BaseStock;
import com.ashcheglov.domain.stock.CommonStock;
import com.ashcheglov.domain.stock.PreferredStock;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Set;

import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static java.util.Collections.synchronizedSet;

/**
 * Assumptions:
 * - the "database" is pre-filled with the sample data
 *   taken from the task description.
 *
 * @author Anton
 * @since 26.09.2016
 */
@Repository
public class StockDaoImpl implements StockDao {

    /**
     * A database emulation
     */
    static Set<BaseStock> stocksStorage = synchronizedSet(new HashSet<>());
    static {
        stocksStorage.add(new CommonStock("TEA", ZERO, valueOf(100)));
        stocksStorage.add(new CommonStock("POP", valueOf(8), valueOf(100)));
        stocksStorage.add(new CommonStock("ALE", valueOf(23), valueOf(60)));
        stocksStorage.add(new PreferredStock("GIN", valueOf(8), valueOf(0.02), valueOf(100)));
        stocksStorage.add(new CommonStock("JOE", valueOf(13), valueOf(250)));
    }

    @Override
    public BaseStock getBySymbol(String stockSymbol) {
        return stocksStorage.stream()
                .filter(stock -> stock.getSymbol().equals(stockSymbol))
                .findAny().orElseThrow(() -> new IllegalArgumentException(
                        String.format("No such stock found [%s]!", stockSymbol)));
    }

    @Override
    public Set<BaseStock> getAll() {
        return stocksStorage;
    }

}
