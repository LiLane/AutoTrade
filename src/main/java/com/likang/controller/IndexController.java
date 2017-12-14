package com.likang.controller;

import com.likang.domain.Symbol;
import com.likang.wrapper.RemoteAccountServiceWrapper;
import com.likang.wrapper.RemoteBusinessServiceWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
}
