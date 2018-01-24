package com.likang.controller;

import com.alibaba.fastjson.JSON;
import com.likang.domain.Consignation;
import com.likang.domain.Symbol;
import com.likang.wrapper.RemoteAccountServiceWrapper;
import com.likang.wrapper.RemoteBusinessServiceWrapper;
import com.likang.wrapper.RemoteOrderServiceWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午8:25
 */
@Controller
@RequestMapping(value = "")
public class IndexController {
    @Resource
    private RemoteAccountServiceWrapper remoteAccountServiceWrapper;
    @Resource
    private RemoteBusinessServiceWrapper remoteBusinessServiceWrapper;
    @Resource
    private RemoteOrderServiceWrapper remoteOrderServiceWrapper;

    @RequestMapping(value = "")
    @ResponseBody
    public String index() {
        long accountId = remoteAccountServiceWrapper.getSpotAccountId();
        remoteAccountServiceWrapper.getAsset(accountId);
        Symbol symbol = new Symbol();
        symbol.setBaseCurrency("eos");
        symbol.setQuoteCurrency("btc");
        remoteBusinessServiceWrapper.getMarket(symbol);
        remoteBusinessServiceWrapper.getSymbols();
        return "Test";
    }

    @RequestMapping(value = "/cancel")
    @ResponseBody
    public String cancel() {
        List<Symbol> symbolList = remoteBusinessServiceWrapper.getSymbols();
        for (Symbol symbol : symbolList) {
            List<Consignation> consignations = remoteOrderServiceWrapper.getConsignations(symbol);
            for (Consignation consignation : consignations) {
                remoteOrderServiceWrapper.cancel(consignation.getId());
            }
        }
        return "Test";
    }
}
