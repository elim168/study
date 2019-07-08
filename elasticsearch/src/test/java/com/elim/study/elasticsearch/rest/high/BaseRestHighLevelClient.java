package com.elim.study.elasticsearch.rest.high;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.After;
import org.junit.Before;

import java.io.IOException;

public abstract class BaseRestHighLevelClient {

    protected RestHighLevelClient highLevelClient;

    @Before
    public void init() {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        this.highLevelClient = new RestHighLevelClient(restClientBuilder);
    }

    @After
    public void after() throws IOException {
        this.highLevelClient.close();
    }

    protected String toJson(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.PrettyFormat);
    }

    protected void printJson(Object obj) {
        System.out.println(this.toJson(obj));
    }


}
