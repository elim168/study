package com.elim.study.elasticsearch.rest.high;

import com.alibaba.fastjson.util.IOUtils;
import com.sun.org.apache.xpath.internal.operations.String;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeRequest;
import org.elasticsearch.action.admin.indices.analyze.AnalyzeResponse;
import org.elasticsearch.action.admin.indices.analyze.DetailAnalyzeResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class IndicesAPITest extends BaseRestHighLevelClient {

    @Test
    public void testAnalyze() throws Exception {
        AnalyzeRequest request = new AnalyzeRequest();
        request.text("<b>movie title</b>", "ford");
        request.addCharFilter("html_strip");
        request.analyzer("english");

        AnalyzeResponse response = this.highLevelClient.indices().analyze(request, RequestOptions.DEFAULT);
        DetailAnalyzeResponse detailAnalyzeResponse = response.detail();
        this.printJson(detailAnalyzeResponse);

        response.getTokens().forEach(analyzeToken -> this.printJson(analyzeToken));
    }

    @Test
    public void testCreateIndex() throws Exception {
        CreateIndexRequest request = new CreateIndexRequest("twitter");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        request.mapping(
                "{\n" +
                        "  \"properties\": {\n" +
                        "    \"message\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);
        request.alias(new Alias("twitter_alias").filter(QueryBuilders.termQuery("user", "kimchy")));
        CreateIndexResponse response = this.highLevelClient.indices().create(request, RequestOptions.DEFAULT);
        this.printJson(response);
    }

    @Test
    public void deleteIndex() throws Exception {
        DeleteIndexRequest request = new DeleteIndexRequest("twitter");
        AcknowledgedResponse response = this.highLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        this.printJson(response);
    }

    @Test
    public void testExists() throws Exception {
        GetIndexRequest request = new GetIndexRequest("movies");
        boolean exists = this.highLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

}
