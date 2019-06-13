package com.elim.study.elasticsearch.rest.high;

import com.alibaba.fastjson.JSON;
import com.elim.study.elasticsearch.dto.Movie;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.SimpleQueryStringBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RestHighLevelClientTest {

    private RestHighLevelClient highLevelClient;

    @Before
    public void init() {
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        this.highLevelClient = new RestHighLevelClient(restClientBuilder);
    }

    @After
    public void after() throws IOException {
        this.highLevelClient.close();
    }

    @Test
    public void testIndex() throws Exception {
        Movie movie = new Movie();
        movie.setId(System.currentTimeMillis());
        movie.setTitle("movie-title-" + movie.getId());
        movie.setDirector("movie-director-" + movie.getId());
        movie.setYear(Long.valueOf(2019 - movie.getId() % 50).intValue());
        movie.setCreateTime(new Date());

        IndexRequest request = new IndexRequest("movies");
        request.id(String.valueOf(movie.getId()));
        String jsonString = JSON.toJSONString(movie);
        request.source(jsonString, XContentType.JSON);
//        request.routing("abc");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
//        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
        request.opType(DocWriteRequest.OpType.INDEX);
        IndexResponse response = highLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(response);


        highLevelClient.indexAsync(request, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                System.out.println("索引成功：" + indexResponse);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("索引失败：" + e);
            }
        });
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    public void testGet() throws Exception {
        GetRequest request = new GetRequest("movies", "1560328153163");
//        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
/*
        //包含特定属性
        String[] includes = {"id", "title", "year"};
        String[] excludes = Strings.EMPTY_ARRAY;
        request.fetchSourceContext(new FetchSourceContext(true, includes, excludes));*/

        //排除某个特定的属性
//        request.fetchSourceContext(new FetchSourceContext(true, Strings.EMPTY_ARRAY, new String[]{"year"}));

        //指定需要返回的属性，这个必须是单独存储的
//        request.storedFields("title", "year");

//        request.version(2);

        GetResponse response = this.highLevelClient.get(request, RequestOptions.DEFAULT);

//        this.highLevelClient.getAsync();

        System.out.println(response.getFields());
        System.out.println(response.getField("title"));
        System.out.println(response);
        Movie movie = JSON.parseObject(response.getSourceAsString(), Movie.class);
        System.out.println(movie);
    }

    @Test
    public void testExists() throws Exception {
        GetRequest request = new GetRequest("movies", "1");
        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        request.storedFields("none");
        boolean exists = this.highLevelClient.exists(request, RequestOptions.DEFAULT);
//        this.highLevelClient.existsAsync();
//        this.highLevelClient.existsSource()
//        this.highLevelClient.existsSourceAsync();
        System.out.println(exists);
    }

    @Test
    public void testDelete() throws Exception {
        DeleteRequest request = new DeleteRequest("movies", "19");
        DeleteResponse response = this.highLevelClient.delete(request, RequestOptions.DEFAULT);
//        this.highLevelClient.deleteAsync();


        System.out.println(response);

    }

    @Test
    public void testUpdateWithScript() throws Exception {
        UpdateRequest request = new UpdateRequest("movies", "18");
        Map<String, Object> params = new HashMap<>();
        params.put("year", 10);
        Script script = new Script(ScriptType.INLINE, "painless", "ctx._source.year+=params.year", params);
        request.script(script);

        //指定响应中是否需要携带返回对应索引的Source
        request.fetchSource(true);
//        request.fetchSource(new FetchSourceContext(true, Strings.EMPTY_ARRAY, new String[]{"year"}));

        request.retryOnConflict(3);//更新冲突时的重试次数。

//        request.scriptedUpsert(true);
//        request.waitForActiveShards(2);
//        request.waitForActiveShards(ActiveShardCount.ALL);

        UpdateResponse response = this.highLevelClient.update(request, RequestOptions.DEFAULT);

        GetResult getResult = response.getGetResult();
        if (getResult.isExists()) {
            System.out.println("指定了fetchSource=true时可以通过getGetResult()获取包含Source的结果：" + getResult.sourceAsString());
        }
//        this.highLevelClient.updateAsync();
        System.out.println(response);
    }

    /**
     * 只更新部分属性
     *
     * @throws Exception
     */
    @Test
    public void testUpdatePartial() throws Exception {
        UpdateRequest request = new UpdateRequest("movies", "182");
        Movie movie = new Movie();
        movie.setYear(2019);
        request.doc(JSON.toJSONString(movie), XContentType.JSON);
//        request.docAsUpsert(true);//加上这句指定ID的索引不存在时将进行新增，否则进行更新。
        UpdateResponse response = this.highLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    /**
     * 当指定的索引不存在时不进行更新操作，转而使用upsert提供的内容进行新增操作。<br/>
     * 否则将使用doc或script提供的内容进行更新操作。
     *
     * @throws Exception
     */
    @Test
    public void testUpdateWithUpsert() throws Exception {
        UpdateRequest request = new UpdateRequest("movies", "180");
        Movie movie = new Movie();
        movie.setTitle("功夫");
        movie.setDirector("周星驰");
        movie.setYear(2004);
        request.upsert(JSON.toJSONString(movie), XContentType.JSON);
        request.doc("createTime", "20190612");
        UpdateResponse response = this.highLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    @Test
    public void testTermVectors() throws Exception {
        TermVectorsRequest request = new TermVectorsRequest("movies", "18");
        request.setFields("title");

//        request.setFieldStatistics(false);
//        request.setTermStatistics(true);
//        request.setPositions(false);
//        request.setOffsets(false);
//        request.setPayloads(false);

        Map<String, Integer> filterSettings = new HashMap<>();
        filterSettings.put("max_num_terms", 23);
        filterSettings.put("min_term_freq", 1);
        filterSettings.put("max_term_freq", 10);
        filterSettings.put("min_doc_freq", 1);
        filterSettings.put("max_doc_freq", 100);
        filterSettings.put("min_word_length", 1);
        filterSettings.put("max_word_length", 10);

        request.setFilterSettings(filterSettings);//对term结果进行一些过滤

        request.setRealtime(false);

        TermVectorsResponse response = this.highLevelClient.termvectors(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response));
//        this.highLevelClient.termvectorsAsync();
        if (response.getFound()) {
            response.getTermVectorsList().forEach(termVector -> {
                System.out.println(termVector.getFieldName() + "==============");
                System.out.println(JSON.toJSONString(termVector.getFieldStatistics()));
                System.out.println(JSON.toJSONString(termVector.getTerms()));
            });
        }
    }

    @Test
    public void testBulk() throws Exception {
        BulkRequest bulkRequest = new BulkRequest();
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            Movie movie = new Movie();
            movie.setId(start + i);
            movie.setTitle("movie-title-" + i);
            movie.setDirector("movie-director-" + i);
            movie.setYear(2018);
            movie.setCreateTime(new Date());
            IndexRequest request = new IndexRequest("movies");
            request.id(movie.getId().toString());
            request.source(JSON.toJSONString(movie), XContentType.JSON);
            bulkRequest.add(request);
        }


        DeleteRequest deleteRequest = new DeleteRequest("movies", "10");
        bulkRequest.add(deleteRequest);

        bulkRequest.timeout("2s");

        BulkResponse bulkResponse = this.highLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);

        System.out.println(bulkResponse);
        bulkResponse.iterator().forEachRemaining(response -> {
            System.out.println(response);
            if (!response.isFailed()) {
                System.out.println(response.getIndex() + "--" + response.getId() + "---" + response.getResponse());
            }
        });

    }

    @Test
    public void testBulkProcessor() throws Exception {
        long t1 = System.currentTimeMillis();
        BulkProcessor.Listener listener = new BulkProcessor.Listener() {
            /**
             * 每一个bulk请求执行之前回调
             * @param executionId
             * @param request
             */
            @Override
            public void beforeBulk(long executionId, BulkRequest request) {
                int numberOfActions = request.numberOfActions();
                System.out.println(String.format("Executing bulk [%s] with %s requests",
                        executionId, numberOfActions));
            }

            /**
             * 每一个bulk请求执行成功后回调
             * @param executionId
             * @param request
             * @param response
             */
            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  BulkResponse response) {
                if (response.hasFailures()) {
                    System.out.println(String.format("Bulk [%s] executed with failures", executionId));
                } else {
                    System.out.println(String.format("Bulk [%s] completed in %s milliseconds",
                            executionId, response.getTook().getMillis()));
                }
            }

            /**
             * 每一个bulk请求执行失败后回调
             * @param executionId
             * @param request
             * @param failure
             */
            @Override
            public void afterBulk(long executionId, BulkRequest request,
                                  Throwable failure) {
                System.out.println("Failed to execute bulk : " + failure);
            }
        };

        BulkProcessor.Builder builder = BulkProcessor.builder((request, bulkListener) -> this.highLevelClient.bulkAsync(request, RequestOptions.DEFAULT, bulkListener), listener);

        //指定请求内容达到1MB时封装为一个bulk请求
        builder.setBulkSize(new ByteSizeValue(1, ByteSizeUnit.MB));
        builder.setBulkActions(10);//指定每10个请求包装成一个Bulk请求
        builder.setConcurrentRequests(2);
        builder.setFlushInterval(TimeValue.timeValueSeconds(10));//指定每10秒发起一次bulk请求。
        builder.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(500), 3));
        BulkProcessor bulkProcessor = builder.build();

        for (int i = 0; i < 30; i++) {
            Movie movie = new Movie();
            movie.setId(System.currentTimeMillis() + i);
            movie.setYear(2010 + i);
            movie.setTitle("movie-title-" + i);
            movie.setDirector("movie-director-" + i);
            movie.setCreateTime(new Date());
            bulkProcessor.add(new IndexRequest("movies").id(movie.getId().toString()).source(JSON.toJSONString(movie), XContentType.JSON));
        }

        boolean result = bulkProcessor.awaitClose(5, TimeUnit.SECONDS);
        if (!result) {
            System.out.println("有部分请求还没有完成就超时了");
        }

        System.out.println("耗时：" + (System.currentTimeMillis() - t1));

    }

    @Test
    public void testMultiGet() throws Exception {
        MultiGetRequest request = new MultiGetRequest();
        request.add("movies", "18");
        request.add("movies", "170");
        //如果需要对单个的Get请求进行更精确的控制，可以通过构造MultiGetRequest.Item来定义。
        request.add(new MultiGetRequest.Item("movies", "16").fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE));
        MultiGetResponse response = this.highLevelClient.mget(request, RequestOptions.DEFAULT);
//        this.highLevelClient.mgetAsync();
        response.iterator().forEachRemaining(multiGetItemResponse -> {
            System.out.println(JSON.toJSONString(multiGetItemResponse));
            if (!multiGetItemResponse.isFailed()) {
                if (multiGetItemResponse.getResponse().isExists()) {
                    System.out.println(multiGetItemResponse.getResponse().getSourceAsString());
                }
            }
        });
    }

    /**
     * 从原索引复制到目标索引
     * @throws Exception
     */
    @Test
    public void testReIndex() throws Exception {
        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices("movies");
        request.setDestIndex("movies2");
//        request.setSourceQuery(new TermQueryBuilder("year", 2010));
        request.setSourceBatchSize(10);//一批次处理10条。
        request.setSize(30);//需要处理的索引数量，默认是不限制。
//        request.addSortField("title", SortOrder.ASC);
        request.setScript(new Script(ScriptType.INLINE, "painless", "ctx._source.year++;", Collections.emptyMap()));
        BulkByScrollResponse response = this.highLevelClient.reindex(request, RequestOptions.DEFAULT);
        System.out.println(response);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testUpdateByQuery() throws Exception {
        UpdateByQueryRequest request = new UpdateByQueryRequest("movies");
//        request.setConflicts("proceed");//proceed or abort
        request.setQuery(new TermQueryBuilder("year", 2012));
        request.setSize(10);//限制更新数量
        request.setBatchSize(100);
        request.setScript(
                new Script(
                        ScriptType.INLINE, "painless",
                        "if (ctx._source.title == 'movie-title-2') {ctx._source.year++;ctx._source.title+='-ABCD';}",
                        Collections.emptyMap()));
        BulkByScrollResponse response = this.highLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response));
    }

    @Test
    public void testDeleteByQuery() throws Exception {
        DeleteByQueryRequest request = new DeleteByQueryRequest("movies2");
//        request.setQuery(new SimpleQueryStringBuilder("movie-title-1|movie-title-3"));
        request.setQuery(new MatchQueryBuilder("title", "title-13"));
        BulkByScrollResponse response = this.highLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response));
    }

}
