package com.likang.task;

import com.likang.domain.Consignation;
import com.likang.domain.Symbol;
import com.likang.service.TradeService;
import com.likang.wrapper.RemoteBusinessServiceWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午9:35
 */
@Component
public class CancelTask implements Runnable {
    @Resource
    private TradeService tradeService;
    @Resource
    private RemoteBusinessServiceWrapper remoteBusinessServiceWrapper;

    public void run() {
        List<Symbol> symbolList = remoteBusinessServiceWrapper.getSymbols();
        for (Symbol symbol : symbolList) {
            List<Consignation> consignations = tradeService.getConsignations(symbol);
            for (Consignation consignation : consignations) {
                tradeService.cancel(consignation.getId());
            }
        }
    }
}
