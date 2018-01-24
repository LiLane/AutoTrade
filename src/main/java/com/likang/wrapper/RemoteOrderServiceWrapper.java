package com.likang.wrapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.likang.client.ApiClient;
import com.likang.domain.Consignation;
import com.likang.domain.Symbol;
import com.likang.domain.request.CreateOrderRequest;
import com.likang.domain.response.ApiResponse;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午9:15
 */
@Service
public class RemoteOrderServiceWrapper {
    @Resource
    private ApiClient client;

    public Long createOrder(CreateOrderRequest request) {
        try {
            ApiResponse<Long> resp =
                    client.post("/v1/order/orders", request, new TypeReference<ApiResponse<Long>>() {
                    });
            return resp.checkAndReturn();
        } catch (Exception e) {
            if (e.getMessage().contains("order amount precision error")) {
                Integer index = e.getMessage().indexOf("scale: `");
                Integer scale = Integer.parseInt(e.getMessage().substring(index + 8, index + 9));
                request.setAmount(new BigDecimal(request.getAmount()).setScale(scale, RoundingMode.HALF_UP)
                        .toPlainString());
                return createOrder(request);
            }
            if (e.getMessage().contains("order price precision error")) {
                Integer index = e.getMessage().indexOf("scale: `");
                Integer scale = Integer.parseInt(e.getMessage().substring(index + 8, index + 9));
                request.setPrice(new BigDecimal(request.getPrice()).setScale(scale, RoundingMode.HALF_UP)
                        .toPlainString());
                return createOrder(request);
            }
            if (e.getMessage().contains("limit order amount error")) {
                return null;
            }
            System.err.println("创建订单失败:" + com.alibaba.fastjson.JSON.toJSONString(request));
            throw e;
        }
    }

    public String placeOrder(long orderId) {
        try {
            ApiResponse<String> resp = client.post("/v1/order/orders/" + orderId + "/place", null,
                    new TypeReference<ApiResponse<String>>() {
                    });
            return resp.checkAndReturn();
        } catch (Exception e) {
            System.err.println("执行订单失败,orderId=" + orderId);
            throw e;
        }
    }

    public List<Consignation> getConsignations(Symbol symbol) {
        Map params = new HashMap<>();
        params.put("symbol", symbol.buildSymbol());
        params.put("states", "submitted,partial-filled");
        ApiResponse<List<Consignation>> consignationApiResponse = client.get("/v1/order/orders", params, new
                TypeReference<ApiResponse<List<Consignation>>>() {
                });
        return consignationApiResponse.checkAndReturn();
    }

    public void cancel(Integer orderId) {
        ApiResponse<Long> resp =
                client.post("/v1/order/orders/" + orderId + "/submitcancel", null, new
                        TypeReference<ApiResponse<Long>>() {
                        });
        return;
    }
}
