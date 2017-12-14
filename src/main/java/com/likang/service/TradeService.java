package com.likang.service;

import com.alibaba.fastjson.JSON;
import com.likang.constant.OrderType;
import com.likang.constant.TradeType;
import com.likang.domain.Trade;
import com.likang.domain.request.CreateOrderRequest;
import com.likang.util.ThreadCache;
import com.likang.wrapper.RemoteOrderServiceWrapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午9:17
 */
@Service
public class TradeService {
    @Resource
    private RemoteOrderServiceWrapper remoteOrderServiceWrapper;

    public void trade(Trade trade) {
        CreateOrderRequest createOrderRequest = buildOrder(trade);
        if (createOrderRequest == null) {
            return;
        }
        long orderId = remoteOrderServiceWrapper.createOrder(createOrderRequest);
        remoteOrderServiceWrapper.placeOrder(orderId);
        System.out.println("交易成功,request=" + JSON.toJSONString(createOrderRequest));
    }

    private CreateOrderRequest buildOrder(Trade trade) {
        if (BigDecimal.valueOf(0.0000001).compareTo(trade.getAmout()) > 0) {
            return null;
        }

        OrderType orderType = TradeType.BUY == trade.getTradeType() ? OrderType.BUY_LIMIT : OrderType.SELL_LIMIT;

        CreateOrderRequest createOrderReq = new CreateOrderRequest();
        createOrderReq.accountId = String.valueOf(ThreadCache.getUserId());
        createOrderReq.amount = trade.getAmout().toPlainString();
        createOrderReq.price = trade.getPrice().toPlainString();
        createOrderReq.symbol = trade.getBase() + trade.getQuote();
        createOrderReq.type = orderType.getOrder();
        return createOrderReq;
    }
}
