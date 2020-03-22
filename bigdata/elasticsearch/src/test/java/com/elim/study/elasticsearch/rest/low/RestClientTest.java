package com.elim.study.elasticsearch.rest.low;

import com.alibaba.fastjson.JSON;
import com.elim.study.elasticsearch.dto.Movie;
import org.apache.http.HttpHost;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.sniff.Sniffer;
import org.junit.Test;

public class RestClientTest {

    @Test
    public void test() throws Exception {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http"))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder.setConnectionRequestTimeout(10 * 1000).setConnectTimeout(5 * 1000)).build();

        Sniffer sniffer = Sniffer.builder(restClient).build();//可定期更新集群节点，默认是5分钟。


        Request request = new Request("GET", "/");
        Response response = restClient.performRequest(request);
        System.out.println(response);
        System.out.println(response.getStatusLine());
        response.getEntity().writeTo(System.out);

        sniffer.close();
        restClient.close();
    }

    @Test
    public void testAddIndex() throws Exception {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();
        for (int i = 0; i < 10; i++) {
            Movie movie = new Movie();
            movie.setId(i + 10L);
            movie.setTitle("movie-title-" + movie.getId());
            movie.setDirector("movie-director-" + movie.getId());
            movie.setYear(2000 + i);
            Request request = new Request("POST", "/movies/movie/" + movie.getId());
            request.setJsonEntity(JSON.toJSONString(movie));
            Response response = restClient.performRequest(request);
            System.out.println(movie.getId() + "----" + response.getStatusLine());
        }
    }

    @Test
    public void testSearch() throws Exception {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200, "http")).build();
        Request request = new Request("GET", "/_search");
        Response response = restClient.performRequest(request);
        StatusLine statusLine = response.getStatusLine();
        System.out.println(statusLine);
        if (statusLine.getStatusCode() == 200) {
            String responseBody = EntityUtils.toString(response.getEntity());
            System.out.println(responseBody);
        }
    }

}
