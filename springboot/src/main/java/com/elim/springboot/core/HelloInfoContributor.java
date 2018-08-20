package com.elim.springboot.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class HelloInfoContributor implements InfoContributor {

    @Override
    public void contribute(Builder builder) {
        Map<String, Object> detailMap = new HashMap<>();
        detailMap.put("map.key1", "value1");
        detailMap.put("map.key2", "value2");
        detailMap.put("map.key1.key1.1", "value");
        
        Map<String, Object> otherMap = new HashMap<>(detailMap);
        detailMap.put("map.key1.key1.2", otherMap);
        
        builder
            .withDetail("key1", "value1")
            .withDetail("key1.key1.1", "value2")
            .withDetails(detailMap);
    }

}
