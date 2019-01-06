package com.elim.learn.springboot.spring.web;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.elim.springboot.mongo.User;

public class RestTemplateTest {

    @Test
    public void testGet() {
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject("https://www.so.com/s?ie=utf-8&q=中国", String.class);
        if (response.length() > 28000) {
            response = response.substring(0, 28000);
        }
        System.out.println(response);
    }
    
    @Test
    public void testGetObject() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(0, new FastJsonHttpMessageConverter());
        User user = restTemplate.getForObject("http://localhost:8081/hello/user/1", User.class);
        System.out.println(user);
    }
    
    @Test
    public void testGet1() {
        RestTemplate restTemplate = new RestTemplate();
        String word = "ABC";
        String response = restTemplate.getForObject("https://www.so.com/s?ie=utf-8&q={word}", String.class, word);
        if (response.length() > 28000) {
            response = response.substring(0, 28000);
        }
        System.out.println(response);
    }
    
    @Test
    public void testGet2() {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://www.so.com/s?ie={charset}&q={word}";
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("charset", "utf-8");
        uriVariables.put("word", "ABC");
        String response = restTemplate.getForObject(uri, String.class, uriVariables);
        if (response.length() > 28000) {
            response = response.substring(0, 28000);
        }
        System.out.println(response);
    }
    
    @Test
    public void testGet3() {
        RestTemplate restTemplate = new RestTemplate();
        String uri = "https://www.so.com/s?ie={charset}&q={word}";
        String response = restTemplate.getForObject(uri, String.class, "utf-8", "ABC");
        if (response.length() > 28000) {
            response = response.substring(0, 28000);
        }
        System.out.println(response);
    }
    
    @Test
    public void testResponseEntity() {
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://www.so.com/s?ie=utf-8&q=ABC", String.class);
        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            System.out.println("Get Success");
        }
        
        System.out.println("Headers: ");
        HttpHeaders headers = responseEntity.getHeaders();
        headers.forEach((name, values) -> {
            System.out.println(name + " = " + values);
        });
        
        if (responseEntity.hasBody()) {
            System.out.println("response: ");
            System.out.println(responseEntity.getBody());
        }
    }
    
    @Test
    public void testPostString() {
        RestTemplate restTemplate = new RestTemplate();
        //默认添加的StringHttpMessageConverter使用的是ISO-8859-1字符集，中文会乱码，为了避免该问题，可以添加一个基于UTF-8字符集的
        //StringHttpMessageConverter到MessageConverter列表的头部。
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        String request = "你好";
        Class<String> responseType = String.class;
        String result = restTemplate.postForObject("http://localhost:8081/hello/string", request, responseType);
        System.out.println(result);
    }
    
    @Test
    public void testRequestWithHttpEntity() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("abc", "123");//自定义Header
        headers.add("Content-Type", "text/plain");//标准Header
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        Class<String> responseType = String.class;
        String result = restTemplate.postForObject("http://localhost:8081/hello/header", httpEntity, responseType);
        System.out.println(result);
    }
    
    @Test
    public void testRequestWithHttpEntityForGet() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("abc", "123");//自定义Header
        headers.add("Content-Type", "text/plain");//标准Header
        headers.add("user-agent", "Chrome");
        HttpEntity<Void> httpEntity = new HttpEntity<>(headers);
        Class<String> responseType = String.class;
        
        ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:8081/hello/header", HttpMethod.GET, httpEntity, responseType);
        
        System.out.println(responseEntity);
        
        System.out.println(responseEntity.getBody());
    }
    
    @Test
    public void testHttpComponents() {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10*1000).setConnectionRequestTimeout(30*1000).build();
        HttpClient httpClient = HttpClientBuilder.create().setMaxConnPerRoute(20).setMaxConnTotal(50).setDefaultRequestConfig(requestConfig).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
        
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(requestFactory);
        
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8081/hello/json", String.class);
        
        System.out.println(responseEntity);
        
        
        
        /*AtomicInteger counter = new AtomicInteger();
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<String> totalResults = new ArrayList<>();
        executorService.execute(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    totalResults.add(LocalDateTime.now() + "-----------" + counter.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (counter.get() == 30) {
                    System.exit(0);
                }
            }
        });
        long start = System.currentTimeMillis();
        while ((System.currentTimeMillis() - start) < 30 * 1000) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.execute(() -> {
                ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:8081/hello/json", String.class);
                if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
                    counter.incrementAndGet();
                }
            });
        }
        
        totalResults.forEach(System.out::println);*/
    }
    
}
