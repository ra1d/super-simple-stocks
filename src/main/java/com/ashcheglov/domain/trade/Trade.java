package com.ashcheglov.domain.trade;

import com.ashcheglov.domain.stock.Stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.util.Comparator.comparingLong;

/**
 * A trade, i.e. an operation with stocks.
 *
 * @author Anton
 * @since 18.09.2016
 */
public class Trade implements Comparable<Trade> {

    private final long id;
    private final TradeType type;
    private final Stock stock;
    private final long quantity;
    private final BigDecimal price;
    private final LocalDateTime timeStamp;

    public Trade(long id, TradeType type, Stock stock, long quantity,
                 BigDecimal price, LocalDateTime timeStamp) {
        this.id = id;
        this.type = type;
        this.stock = stock;
        this.quantity = quantity;
        this.price = price;
        this.timeStamp = timeStamp;
    }

    public long getId() {
        return id;
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

    @Override
    public int compareTo(Trade that) {
        return comparingLong(Trade::getId).compare(this, that);
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) return true;
        if (that == null || getClass() != that.getClass()) return false;
        Trade trade = (Trade) that;
        if (id != trade.id) return false;
        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Trade{");
        sb.append("id=").append(id);
        sb.append(", type=").append(type);
        sb.append(", stock=").append(stock);
        sb.append(", quantity=").append(quantity);
        sb.append(", price=").append(price);
        sb.append(", timeStamp=").append(timeStamp);
        sb.append('}');
        return sb.toString();
    }

}
