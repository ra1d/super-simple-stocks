package com.ashcheglov.domain.trade;

import com.ashcheglov.domain.stock.Stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A trade, i.e. an operation with stocks.
 *
 * @author Anton
 * @since 18.09.2016
 */
public class Trade {

    private final TradeType type;
    private final Stock stock;
    private final long quantity;
    private final BigDecimal price;
    private final LocalDateTime timeStamp;

    public Trade(TradeType type, Stock stock, long quantity,
                 BigDecimal price, LocalDateTime timeStamp) {
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.timeStamp = timeStamp;
    }

    public TradeType getType() {
        return type;
    }

    public Stock getStock() {
        return stock;
    }

    public long getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

}
