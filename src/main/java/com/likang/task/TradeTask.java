package com.likang.task;

import com.likang.constant.CoinType;
import com.likang.constant.TradeType;
import com.likang.constant.WalletType;
import com.likang.domain.Symbol;
import com.likang.domain.Trade;
import com.likang.domain.response.AssetResponse;
import com.likang.domain.response.MarketDepthResponse;
import com.likang.service.TradeService;
import com.likang.util.ThreadCache;
import com.likang.wrapper.RemoteAccountServiceWrapper;
import com.likang.wrapper.RemoteBusinessServiceWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午9:35
 */
@Component
public class TradeTask implements Runnable {
    @Resource
    private TradeService tradeService;
    @Resource
    private RemoteBusinessServiceWrapper remoteBusinessServiceWrapper;
    @Resource
    private RemoteAccountServiceWrapper remoteAccountServiceWrapper;

    public void run() {
        long accountId = remoteAccountServiceWrapper.getSpotAccountId();
        AssetResponse assetResponse = remoteAccountServiceWrapper.getAsset(accountId);

        buy(assetResponse);
        sell(assetResponse);
    }

    private void sell(AssetResponse assetResponse) {
        List<AssetResponse.Wallet> wallets = assetResponse.getList();
        for (AssetResponse.Wallet wallet : wallets) {
            if (!WalletType.TRADE.getCode().equals(wallet.getType())) {
                continue;
            }
            Symbol symbol = new Symbol();
            symbol.setBaseCurrency(wallet.getCurrency());
            symbol.setQuoteCurrency(CoinType.BTC.getName());

            MarketDepthResponse marketDepthResponse = remoteBusinessServiceWrapper.getMarket(symbol);
            if (marketDepthResponse == null) {
                continue;
            }
            BigDecimal marketPrice = marketDepthResponse.getSellPrice(0);

            Integer sellIndex = (int) (Math.random() * 10) + 2;
            Integer scale = 13;
            BigDecimal sellPrice = marketPrice.multiply(BigDecimal.valueOf(100 + sellIndex)).divide(BigDecimal.valueOf(100), scale, BigDecimal.ROUND_HALF_UP);
            BigDecimal amout = wallet.getBalance();
            if (sellPrice.multiply(amout).compareTo(BigDecimal.valueOf(0.01)) < 0) {
                continue;
            }

            Trade trade = new Trade();
            trade.setAmout(amout);
            trade.setPrice(sellPrice);
            trade.setTradeType(TradeType.SELL);
            trade.setBase(symbol.getBaseCurrency());
            trade.setQuote(symbol.getQuoteCurrency());

            trade(trade, scale, assetResponse.getUserId());
        }
    }

    private void trade(Trade trade, Integer scale, Long accountId) {
        try {
            BigDecimal amount = trade.getAmout().setScale(scale, RoundingMode.FLOOR);
            trade.setAmout(amount);
            tradeService.trade(trade, accountId);
        } catch (Exception e) {
            if (scale > 0) {
                trade(trade, scale - 1, accountId);
            }
        }
    }

    private void buy(AssetResponse assetResponse) {
        AssetResponse.Wallet wallet = assetResponse.getTradeWallet(CoinType.BTC.getName());
        if (wallet.getBalance().compareTo(BigDecimal.valueOf(0.01)) < 0) {
            System.out.println("btc余额不足,balance=" + wallet.getBalance());
            return;
        }
        Symbol symbol = chooseOneSymbol();
        MarketDepthResponse marketDepthResponse = remoteBusinessServiceWrapper.getMarket(symbol);
        BigDecimal marketPrice = marketDepthResponse.getBuyPrice(0);

        Integer buyIndex = (int) (Math.random() * 5) + 1;
        BigDecimal buyPrice = marketPrice.multiply(BigDecimal.valueOf(100 - buyIndex)).divide(BigDecimal.valueOf(100), 7, BigDecimal.ROUND_HALF_UP);
        BigDecimal amout = BigDecimal.valueOf(0.01).divide(buyPrice, 7, BigDecimal.ROUND_HALF_UP);

        Trade trade = new Trade();
        trade.setAmout(amout);
        trade.setPrice(buyPrice);
        trade.setTradeType(TradeType.BUY);
        trade.setBase(symbol.getBaseCurrency());
        trade.setQuote(symbol.getQuoteCurrency());

        tradeService.trade(trade, assetResponse.getUserId());
    }

    private Symbol chooseOneSymbol() {
        List<Symbol> totalSymbols = remoteBusinessServiceWrapper.getSymbols();
        if (totalSymbols == null || totalSymbols.size() == 0) {
            return null;
        }
        List<Symbol> symbols = new ArrayList<>();
        for (Symbol symbol : totalSymbols) {
            if (CoinType.BTC.getName().equals(symbol.quoteCurrency)) {
                symbols.add(symbol);
            }
        }
        Integer size = symbols.size();
        Integer index = (int) (Math.random() * size);
        return symbols.get(index);
    }
}
