package com.elim.study.elasticsearch.rest.high;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.http.HttpHost;
import org.apache.lucene.search.Explanation;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.fieldcaps.FieldCapabilities;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.rankeval.*;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAPITest extends BaseRestHighLevelClient {

    @Test
    public void testSearchRequest() throws Exception {

        SearchRequest request = new SearchRequest("movies");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", "ford");
//        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
//        matchQueryBuilder.prefixLength(3);
//        matchQueryBuilder.maxExpansions(10);
//
//        searchSourceBuilder.query(matchQueryBuilder);
        searchSourceBuilder.size(8);//返回记录数
        searchSourceBuilder.timeout(TimeValue.timeValueSeconds(3));

//        searchSourceBuilder.sort("_id", SortOrder.DESC);
        searchSourceBuilder.sort("year", SortOrder.DESC);
//        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));//根据匹配度倒序排列

        request.source(searchSourceBuilder);
        request.preference("_local");
        SearchResponse response = this.highLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(this.toJson(response));
        System.out.println(this.toJson(response.getAggregations()));
        System.out.println(this.toJson(response.getSuggest()));
    }

    @Test
    public void testHighLight() throws Exception {
        SearchRequest request = new SearchRequest("movies");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "ford"));
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title", 20);
        HighlightBuilder.Field highlightYear = new HighlightBuilder.Field("year");
        highlightYear.highlighterType("unified");
        highlightBuilder.field(highlightYear);
        searchSourceBuilder.highlighter(highlightBuilder);

        request.source(searchSourceBuilder);
        SearchResponse response = this.highLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(this.toJson(response));

        SearchHits hits = response.getHits();
        for (SearchHit hit : hits.getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField highlight = highlightFields.get("title");
            Text[] fragments = highlight.fragments();
            String fragmentString = fragments[0].string();
            System.out.println(fragmentString);
        }
    }

    @Test
    public void testAggregation() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("by_year")//聚合后的结果的名称
                .field("year");//通过year分组
        aggregationBuilder.subAggregation(AggregationBuilders.count("count_by_year")//聚合后的结果的名称
                .field("year"));//分组后计数
        searchSourceBuilder.aggregation(aggregationBuilder);

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        SearchRequest request = new SearchRequest("movies");
        request.source(searchSourceBuilder);
        SearchResponse response = this.highLevelClient.search(request, RequestOptions.DEFAULT);

        SearchHits hits = response.getHits();
        System.out.println("命中的查询结果：" + this.toJson(hits));

        Aggregations aggregations = response.getAggregations();
        System.out.println("聚合后的结果：" + this.toJson(aggregations));

        Terms aggregation = aggregations.get("by_year");
        Terms.Bucket bucket = aggregation.getBucketByKey("1962");
        //Bucket返回的Aggregations和顶级的Aggregations不是同一个。
        ParsedValueCount countByYear = bucket.getAggregations().get("count_by_year");
        System.out.println(countByYear.getValue());
    }

    @Test
    public void testSuggestion() throws Exception {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestionBuilder suggestionBuilder = SuggestBuilders.termSuggestion("title").text("ford");
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggest_title", suggestionBuilder);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.suggest(suggestBuilder);

        SearchRequest request = new SearchRequest("movies");
        request.source(searchSourceBuilder);
        SearchResponse response = this.highLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(response.getSuccessfulShards());
        Suggest suggest = response.getSuggest();
        System.out.println(this.toJson(suggest));
        System.out.println(this.toJson(suggest.getSuggestion("suggest_title")));
        suggest.getSuggestion("suggest_title").iterator().forEachRemaining(entry -> {
            System.out.println(entry.getText().string());
        });
    }

    /**
     * 通过scroll可以分次获取查询出来的所有的内容。
     * @throws Exception
     */
    @Test
    public void testSearchScroll() throws Exception {
        int pageSize = 5;
        int pageNo = 1;
        SearchRequest request = new SearchRequest("movies");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "movie"));
        searchSourceBuilder.size(pageSize);
        request.source(searchSourceBuilder);
        request.scroll(TimeValue.timeValueMinutes(1));

        SearchResponse response = this.highLevelClient.search(request, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        long hitsTotal = hits.getTotalHits().value;

        System.out.println("总命中数: " + hitsTotal);
        long totalPage = hitsTotal%pageSize == 0 ? hitsTotal/pageSize : (hitsTotal/pageSize + 1);
        String scrollId = response.getScrollId();
        while (pageNo <= totalPage) {
            System.out.println(scrollId);
            for (SearchHit hit : hits) {
                System.out.println(pageNo + "-------" + hit.getId() + "--------" + hit.getSourceAsString());
            }
            if (++pageNo <= totalPage) {
                SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
                searchScrollRequest.scroll(TimeValue.timeValueMinutes(1));
                response = this.highLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
                hits = response.getHits();
                scrollId = response.getScrollId();
            }
        }

        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.addScrollId(scrollId);
        ClearScrollResponse clearScrollResponse = this.highLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        System.out.println(clearScrollResponse.isSucceeded());
    }

    @Test
    public void testMultiSearch() throws Exception {
        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();
        SearchRequest request1 = new SearchRequest("movies");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "movie"));
        request1.source(searchSourceBuilder);
        multiSearchRequest.add(request1);

        SearchRequest request2 = new SearchRequest("schools");
        SearchSourceBuilder searchSourceBuilder2 = new SearchSourceBuilder();
        searchSourceBuilder2.query(QueryBuilders.matchAllQuery());
        request2.source(searchSourceBuilder2);
        multiSearchRequest.add(request2);

        MultiSearchResponse response = this.highLevelClient.msearch(multiSearchRequest, RequestOptions.DEFAULT);
        MultiSearchResponse.Item[] items = response.getResponses();
        for (MultiSearchResponse.Item item : items) {
            if (item.isFailure()) {
                item.getFailure().printStackTrace();
            } else {
                SearchResponse _response = item.getResponse();
                System.out.println(_response);
                SearchHit[] hits = _response.getHits().getHits();
                for (SearchHit hit : hits) {
                    System.out.println(hit.getSourceAsString());
                }
            }
        }
    }

    @Test
    public void testSearchTemplate() throws Exception {
        SearchTemplateRequest request = new SearchTemplateRequest();
        request.setRequest(new SearchRequest("movies"));
        request.setScriptType(ScriptType.INLINE);
        request.setScript(
                "{" +
                        "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                        "  \"size\" : \"{{size}}\"" +
                        "}");
        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("field", "title");
        scriptParams.put("value", "movie");
        scriptParams.put("size", 5);
        request.setScriptParams(scriptParams);
        SearchTemplateResponse searchTemplateResponse = this.highLevelClient.searchTemplate(request, RequestOptions.DEFAULT);
        if (searchTemplateResponse.hasResponse()) {
            SearchResponse response = searchTemplateResponse.getResponse();
            SearchHits hits = response.getHits();
            hits.forEach(hit -> System.out.println(hit.getSourceAsString()));
        }
    }

    @Test
    public void testMultiSearchTemplateRequest() throws Exception {

        MultiSearchTemplateRequest multiSearchTemplateRequest = new MultiSearchTemplateRequest();
        SearchTemplateRequest request1 = new SearchTemplateRequest();
        request1.setRequest(new SearchRequest("movies"));
        request1.setScriptType(ScriptType.INLINE);
        request1.setScript(
                "{" +
                        "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                        "  \"size\" : \"{{size}}\"" +
                        "}");
        Map<String, Object> scriptParams = new HashMap<>();
        scriptParams.put("field", "title");
        scriptParams.put("value", "movie");
        scriptParams.put("size", 5);
        request1.setScriptParams(scriptParams);

        multiSearchTemplateRequest.add(request1);//可以添加多个SearchTemplateRequest

        MultiSearchTemplateResponse response = this.highLevelClient.msearchTemplate(multiSearchTemplateRequest, RequestOptions.DEFAULT);
        response.iterator().forEachRemaining(item -> {
            SearchTemplateResponse _response = item.getResponse();
            SearchResponse searchResponse = _response.getResponse();
            searchResponse.getHits().forEach(hit -> System.out.println(hit.getSourceAsString()));
        });

    }

    @Test
    public void testFieldCapabilitiesRequest() throws Exception {
        FieldCapabilitiesRequest request = new FieldCapabilitiesRequest();
        request.fields("title", "year").indices("movies");
        FieldCapabilitiesResponse response = this.highLevelClient.fieldCaps(request, RequestOptions.DEFAULT);

        Map<String, FieldCapabilities> titleCapabilities = response.getField("title");
        System.out.println(this.toJson(titleCapabilities));

        FieldCapabilities titleCapa = titleCapabilities.get("text");
        System.out.println(titleCapa.isAggregatable());
        System.out.println(titleCapa.isSearchable());


        Map<String, FieldCapabilities> yearCapabilities = response.getField("year");
        System.out.println(this.toJson(yearCapabilities));
    }

    @Test
    public void testRankEvalRequest() throws Exception {

        EvaluationMetric evaluationMetric = new PrecisionAtK();
        List<RatedDocument> ratedDocs = new ArrayList<>();
        ratedDocs.add(new RatedDocument("movies", "1560773542234", 1));
        ratedDocs.add(new RatedDocument("movies", "1560773542235", 1));
        ratedDocs.add(new RatedDocument("movies", "1560773542236", 1));
        ratedDocs.add(new RatedDocument("movies", "1560773542237", 1));
        ratedDocs.add(new RatedDocument("movies", "1560773542238", 1));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "movie"));

        RatedRequest ratedRequest = new RatedRequest("movie_query", ratedDocs, searchSourceBuilder);
        List<RatedRequest> ratedRequests = new ArrayList<>();
        ratedRequests.add(ratedRequest);
        RankEvalSpec rankEvalSpec = new RankEvalSpec(ratedRequests, evaluationMetric);
        RankEvalRequest rankEvalRequest = new RankEvalRequest(rankEvalSpec, new String[]{"movies"});
        RankEvalResponse rankEvalResponse = this.highLevelClient.rankEval(rankEvalRequest, RequestOptions.DEFAULT);
        System.out.println(rankEvalResponse.getMetricScore());
        Map<String, EvalQueryQuality> partialResults = rankEvalResponse.getPartialResults();
        EvalQueryQuality evalQueryQuality = partialResults.get("movie_query");
        System.out.println(this.toJson(evalQueryQuality));

        System.out.println(evalQueryQuality.metricScore());

    }

    @Test
    public void testExplainRequest() throws Exception {
        ExplainRequest request = new ExplainRequest("movies", "1");
        request.query(QueryBuilders.matchQuery("title", "Godfather"));
        ExplainResponse response = this.highLevelClient.explain(request, RequestOptions.DEFAULT);
        System.out.println(response.hasExplanation());
        System.out.println(response.isExists() + "-----isExists");
        System.out.println(response.isMatch() + "-----isMatch");
        System.out.println(response.status() + "-----status");
        Explanation explanation = response.getExplanation();
        System.out.println(this.toJson(explanation));
    }

    @Test
    public void testCountRequest() throws Exception {
        CountRequest request = new CountRequest("movies");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("title", "movie ford"));
        request.source(searchSourceBuilder);
        CountResponse response = this.highLevelClient.count(request, RequestOptions.DEFAULT);
        long count = response.getCount();
        System.out.println("满足查询条件的记录数是：" + count);
    }

}
