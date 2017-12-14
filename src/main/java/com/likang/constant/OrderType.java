package com.likang.constant;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午9:16
 */
public enum OrderType {
    BUY_LIMIT("buy-limit", "限价买入"),
    SELL_LIMIT("sell-limit", "限价卖出"),
    BUY_MARKET("buy-market", "市价买入"),
    SELL_MARKET("sell-market", "市价卖出");

    private String order;
    private String desc;

    OrderType(String order, String desc) {
        this.order = order;
        this.desc = desc;
    }

    public String getOrder() {
        return order;
    }

    public String getDesc() {
        return desc;
    }
}
