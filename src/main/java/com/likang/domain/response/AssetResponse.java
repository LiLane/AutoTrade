package com.likang.domain.response;

import com.likang.constant.WalletType;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: likang
 * Date: 17/12/9
 * Time: 下午10:05
 */
public class AssetResponse {
    private Long id;
    private String type;
    private String state;
    private List<Wallet> list;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Wallet> getList() {
        return list;
    }

    public void setList(List<Wallet> list) {
        this.list = list;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Wallet getTradeWallet(String currency) {
        List<Wallet> wallets = getList();
        for (Wallet wallet : wallets) {
            if (!WalletType.TRADE.getCode().equals(wallet.getType())) {
                continue;
            }
            if (currency.equals(wallet.getCurrency())) {
                return wallet;
            }
        }
        return null;
    }

    public static class Wallet {
        private String currency;
        private String type;
        private BigDecimal balance;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }
    }
}
