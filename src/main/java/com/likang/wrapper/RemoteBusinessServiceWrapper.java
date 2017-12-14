package com.likang.wrapper;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.likang.client.ApiClient;
import com.likang.domain.Symbol;
import com.likang.domain.response.ApiResponse;
import com.likang.domain.response.MarketDepthResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午9:09
 */
@Service
public class RemoteBusinessServiceWrapper {

    @Resource
    private ApiClient client;

    public MarketDepthResponse getMarket(Symbol symbol) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("symbol", symbol.buildSymbol());
        params.put("type", "step1");

        try {
            String resp = client.get("/market/depth", params);
            MarketDepthResponse marketDepth = com.alibaba.fastjson.JSON.parseObject(resp, new com.alibaba.fastjson.TypeReference<MarketDepthResponse>() {
            });
            if ("error".equals(marketDepth.getStatus())) {
                return null;
            }
//            System.out.println("查询行情,marketDepth=" + JSON.toJSONString(marketDepth));
            return marketDepth;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Symbol> getSymbols() {
        ApiResponse<List<Symbol>> resp =
                client.get("/v1/common/symbols", null, new TypeReference<ApiResponse<List<Symbol>>>() {
                });
        List<Symbol> symbols = resp.checkAndReturn();
//        System.out.println("查询交易对,symbols=" + JSON.toJSONString(symbols));
        return symbols;
    }

}
