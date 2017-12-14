package com.likang.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: likang
 * Date: 17/12/13
 * Time: 下午9:26
 */
public class ThreadLocalUtil {
    private static final ThreadLocal<ThreadLocalUtil.ThreadContext> CACHE = new ThreadLocal() {
        protected ThreadLocalUtil.ThreadContext initialValue() {
            return new ThreadLocalUtil.ThreadContext();
        }
    };

    protected ThreadLocalUtil() {
    }

    public static void setReadonly(boolean readonly) {
        ((ThreadLocalUtil.ThreadContext) CACHE.get()).readonly = readonly;
    }

    public static boolean isReadonly() {
        return ((ThreadLocalUtil.ThreadContext) CACHE.get()).readonly;
    }

    public static void release() {
        CACHE.remove();
    }

    public static void setData(String key, Object data) {
        ((ThreadLocalUtil.ThreadContext) CACHE.get()).data.put(key, data);
    }

    public static Object getData(String key) {
        return ((ThreadLocalUtil.ThreadContext) CACHE.get()).data.get(key);
    }

    private static class ThreadContext {
        Map<String, Object> data;
        boolean readonly;

        private ThreadContext() {
            this.data = new ConcurrentHashMap();
        }
    }
}