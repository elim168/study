package com.elim.learn.spring.cloud.client.config;

public class RequestIdHolder {

    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();
    
    public static String get() {
        return REQUEST_ID.get();
    }
    
    public static void set(String requestId) {
        REQUEST_ID.set(requestId);
    }
    
}
