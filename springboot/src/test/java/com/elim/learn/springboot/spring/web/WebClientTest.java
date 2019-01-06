package com.elim.learn.springboot.spring.web;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.elim.springboot.mongo.User;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class WebClientTest {

    @Test
    public void testGet() throws Exception {
        WebClient webClient = WebClient.create();
        Mono<String> mono = webClient.get().uri("https://www.baidu.com").retrieve().bodyToMono(String.class);
        mono.subscribe(System.out::println);
        System.out.println("===============================1");
        TimeUnit.SECONDS.sleep(2);
        System.out.println("===============================2");
    }

    @Test
    public void testGetObject() throws Exception {
        String baseUrl = "http://localhost:8081";
        WebClient webClient = WebClient.create(baseUrl);
        Mono<User> mono = webClient.get().uri("hello/user/{id}", 1).retrieve().bodyToMono(User.class);
        User user = mono.block();
        System.out.println(user);
    }

    @Test
    public void testGetUsers() throws Exception {
        String baseUrl = "http://localhost:8081";
        WebClient webClient = WebClient.create(baseUrl);
        Flux<User> userFlux = webClient.get().uri("hello/users").retrieve().bodyToFlux(User.class);
        userFlux.subscribe(System.out::println);
        List<User> users = userFlux.collectList().block();
        System.out.println(users);
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void testPost() throws Exception {
        String baseUrl = "http://localhost:8081";
        WebClient webClient = WebClient.create(baseUrl);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "u123");
        map.add("password", "p123");

        Mono<String> mono = webClient.post().uri("/login").syncBody(map).retrieve().bodyToMono(String.class);
        System.out.println(mono.block());

    }

    @Test
    public void postJson() throws Exception {
        String baseUrl = "http://localhost:8081";
        WebClient webClient = WebClient.create(baseUrl);

        String userJson = "{" + "    \"name\":\"张三\",\r\n" + "    \"username\":\"zhangsan\"\r\n" + "}";

        Mono<Void> mono = webClient.post().uri("/hello/user").contentType(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(userJson).retrieve().bodyToMono(Void.class);
        mono.block();
    }

    public void postLogin() {
        String baseUrl = "http://localhost:8081";
        WebClient webClient = WebClient.create(baseUrl);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username", "u123");
        map.add("password", "p123");

        Mono<ClientResponse> mono = webClient.post().uri("login").syncBody(map).exchange();
        ClientResponse response = mono.block();
        if (response.statusCode() == HttpStatus.OK) {
            Mono<Result> resultMono = response.bodyToMono(Result.class);
            resultMono.subscribe(result -> {
                if (result.isSuccess()) {
                    ResponseCookie sidCookie = response.cookies().getFirst("sid");
                    Flux<User> userFlux = webClient.get().uri("users").cookie(sidCookie.getName(), sidCookie.getValue())
                            .retrieve().bodyToFlux(User.class);
                    userFlux.subscribe(System.out::println);
                }
            });
        }
    }

    @Data
    private static class Result {
        private boolean success;
    }

    @Test
    public void customBuild() {
        String baseUrl = "http://localhost:8081";
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).filter((request, next) -> {
            System.out.println(request.url());
            ClientRequest newRequest = ClientRequest.from(request).header("header1", "value1").build();
            Mono<ClientResponse> responseMono = next.exchange(newRequest);
            return Mono.fromCallable(() -> {
                ClientResponse response = responseMono.block();
                ClientResponse newResponse = ClientResponse.from(response).header("responseHeader1", "Value1").build();
                return newResponse;
            });
        }).build();
        Mono<String> mono = webClient.get().uri("hello/header").attribute("attrName", "attrValue").retrieve().bodyToMono(String.class);
        System.out.println(mono.block() + "-----------------------------");
        
        Mono<ClientResponse> monoResponse = webClient.get().uri("hello/header").exchange();
        ClientResponse response = monoResponse.block();
        System.out.println(response.headers().header("responseHeader1"));
    }
    
}
