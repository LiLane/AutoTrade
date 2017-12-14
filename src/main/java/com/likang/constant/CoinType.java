package com.likang.constant;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 上午11:54
 */
public enum CoinType {
    BTC(1, "btc", "比特币");
    private Integer id;
    private String name;
    private String desc;

    CoinType(Integer id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
