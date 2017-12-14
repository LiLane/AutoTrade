package com.likang;

import com.likang.util.ThreadCache;
import com.likang.wrapper.RemoteAccountServiceWrapper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午9:23
 */
@Component
public class UserInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private RemoteAccountServiceWrapper remoteAccountServiceWrapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        long accountId = remoteAccountServiceWrapper.getSpotAccountId();
        ThreadCache.setUserId(accountId);
        return true;
    }
}
