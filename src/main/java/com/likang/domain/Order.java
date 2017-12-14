package com.likang.domain;

import java.math.BigDecimal;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午12:04
 */
public class Order {
    private Integer id;
    private BigDecimal price;
    private BigDecimal amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
