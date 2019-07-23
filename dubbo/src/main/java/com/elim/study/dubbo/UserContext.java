package com.elim.study.dubbo;

public class UserContext {

    private final static ThreadLocal<Long> ID_HOLDER = new ThreadLocal<Long>() {
        @Override
        protected Long initialValue() {
            return 123456L;
        }
    };

    public static void setUserId(Long userId) {
        ID_HOLDER.set(userId);
    }

    public static Long getUserId() {
        return ID_HOLDER.get();
    }

}
