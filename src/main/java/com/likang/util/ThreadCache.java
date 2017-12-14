package com.likang.util;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午9:27
 */
@SuppressWarnings("unchecked")
public class ThreadCache extends ThreadLocalUtil{
    private static long userId;

    public static long getUserId() {
        return userId;
    }

    public static void setUserId(long userId) {
        ThreadCache.userId = userId;
    }
}
