package com.likang.domain;

import com.likang.constant.CoinType;
import com.likang.constant.TradeType;

import java.math.BigDecimal;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 上午11:53
 */
public class Trade {
    private Integer id;
    private String base;
    private String quote;
    private BigDecimal price;
    private BigDecimal amout;
    private TradeType tradeType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmout() {
        return amout;
    }

    public void setAmout(BigDecimal amout) {
        this.amout = amout;
    }

    public TradeType getTradeType() {
        return tradeType;
    }

    public void setTradeType(TradeType tradeType) {
        this.tradeType = tradeType;
    }
}
