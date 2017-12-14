package com.likang.domain.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午12:02
 */
public class MarketDepthResponse {
    private String status;
    private Long ts;
    private String ch;
    private Tick tick;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public String getCh() {
        return ch;
    }

    public void setCh(String ch) {
        this.ch = ch;
    }

    public Tick getTick() {
        return tick;
    }

    public void setTick(Tick tick) {
        this.tick = tick;
    }

    public BigDecimal getBuyPrice(Integer index) {
        Tick tick = getTick();
        List<List<BigDecimal>> asks = tick.getAsks();
        List<BigDecimal> ask = asks.get(index);
        return ask.get(0);
    }

    public BigDecimal getSellPrice(Integer index) {
        Tick tick = getTick();
        List<List<BigDecimal>> bids = tick.getBids();
        List<BigDecimal> bid = bids.get(index);
        return bid.get(index);
    }

    public static class Tick {
        private Long id;
        private Long ts;
        private List<List<BigDecimal>> bids;
        private List<List<BigDecimal>> asks;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Long getTs() {
            return ts;
        }

        public void setTs(Long ts) {
            this.ts = ts;
        }

        public List<List<BigDecimal>> getBids() {
            return bids;
        }

        public void setBids(List<List<BigDecimal>> bids) {
            this.bids = bids;
        }

        public List<List<BigDecimal>> getAsks() {
            return asks;
        }

        public void setAsks(List<List<BigDecimal>> asks) {
            this.asks = asks;
        }
    }
}