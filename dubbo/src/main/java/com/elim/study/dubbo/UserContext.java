package com.elim.study.dubbo;

public class UserContext {

    private final static ThreadLocal<Long> ID_HOLDER = new ThreadLocal<>();

    public static void setUserId(Long userId) {
        ID_HOLDER.set(userId);
    }

    public static Long getUserId() {
        return ID_HOLDER.get();
    }

}
