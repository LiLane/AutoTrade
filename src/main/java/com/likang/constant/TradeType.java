package com.likang.constant;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 上午11:58
 */
public enum TradeType {
    BUY(1, "购进"),
    SELL(2, "售出");
    private Integer id;
    private String desc;

    TradeType(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }
}
