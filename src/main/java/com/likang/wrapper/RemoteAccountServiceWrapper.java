package com.likang.wrapper;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.likang.client.ApiClient;
import com.likang.domain.response.AccountResponse;
import com.likang.domain.response.ApiResponse;
import com.likang.domain.response.AssetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午8:46
 */
@Service
public class RemoteAccountServiceWrapper {

    @Resource
    private ApiClient client;

    public List<AccountResponse> getAccounts() {
        try {
            ApiResponse<List<AccountResponse>> resp =
                    client.get("/v1/account/accounts", null, new TypeReference<ApiResponse<List<AccountResponse>>>() {
                    });
            List<AccountResponse> accountResponses = resp.checkAndReturn();
//            System.out.println("查询账号信息,accountResponses=" + JSON.toJSONString(accountResponses));
            return accountResponses;
        } catch (Exception e) {
            return new ArrayList<AccountResponse>();
        }
    }

    public long getSpotAccountId() {
        try {
            List<AccountResponse> accountResponses = getAccounts();
            for (AccountResponse accountResponse : accountResponses) {
                if ("spot".equals(accountResponse.getType())) {
                    return accountResponse.getId();
                }
            }
            return 632935l;
        } catch (Exception e) {
            return 632935l;
        }
    }

    public AssetResponse getAsset(Long accountId) {
        ApiResponse<AssetResponse> resp =
                client.get("/v1/account/accounts/" + accountId + "/balance", null, new TypeReference<ApiResponse<AssetResponse>>() {
                });
        AssetResponse assetResponse = resp.checkAndReturn();
        assetResponse.setUserId(accountId);
//        System.out.println("查询账号余额信息,assetResponse=" + JSON.toJSONString(assetResponse));
        return assetResponse;
    }
}
