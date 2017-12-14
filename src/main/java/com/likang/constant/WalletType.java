package com.likang.constant;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午8:44
 */
public enum WalletType {
    TRADE(1, "trade", "可交易"),
    FROZEN(2, "frozen", "冻结");
    private Integer id;
    private String code;
    private String desc;

    WalletType(Integer id, String code, String desc) {
        this.id = id;
        this.code = code;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
