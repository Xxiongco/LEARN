package com.panda;

import com.alibaba.fastjson.JSON;
import com.panda.domain.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SpringBootTest
class SpringBootEsApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    void contextLoads() {
    }

    // 测试索引的创建（不带mapping，ElasticSearch默认会根据你的添加的文档来创建mapping）
    @Test
    void testCreateIndex() throws IOException {
        // 创建索引的请求
        CreateIndexRequest nan_index = new CreateIndexRequest("nan_index");
        // client执行请求
        CreateIndexResponse response = restHighLevelClient.indices().create(nan_index, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    // 带上自定义的mapping来创建索引
    @Test
    void testCreateMappingIndex() throws IOException {
        // 创建索引的请求
        CreateIndexRequest indexRequest = new CreateIndexRequest("nanmapping_index");
        // mapping规则去别的地方写好之后，复制粘贴过来，IDEA会自动转义相关符号
        String mapping = "{\n" +
                "    \"properties\": {\n" +
                "      \"name\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"age\": {\n" +
                "        \"type\": \"integer\"\n" +
                "      }\n" +
                "    }\n" +
                "  }";
        // 添加索引的mapping规则
        indexRequest.mapping(mapping, XContentType.JSON);
        // 发送请求
        CreateIndexResponse response = restHighLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    // 测试索引是否存在
    @Test
    void testExitIndex() throws IOException {
        // 获取索引的请求
        GetIndexRequest nan_index = new GetIndexRequest("nan_index");
        // 执行请求
        boolean exists = restHighLevelClient.indices().exists(nan_index, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    // 测试删除索引
    @Test
    void testDeleteIndex() throws IOException {
        // 删除索引的请求
        DeleteIndexRequest nan_index = new DeleteIndexRequest("nan_index");
        // 执行删除的请求
        AcknowledgedResponse response = restHighLevelClient.indices().delete(nan_index, RequestOptions.DEFAULT);
        System.out.println(response);
    }

    // 测试文档的添加
    @Test
    public void testCreateDoc() throws IOException {
        // 准备好数据
        User user = User.builder()
                .name("小杰")
                .age(23)
                .describe("你要是这么想我也没办法")
                .tag(new String[]{"md", "渣男"})
                .build();

        // 创建好index请求
        IndexRequest indexRequest = new IndexRequest("nan_index");
        // 设置索引
        indexRequest.id("1");
        // 设置超时时间（默认）
        indexRequest.timeout(TimeValue.timeValueSeconds(5));
        // 往请求中添加数据
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        //执行添加请求
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    //获取简单文档数据
    @Test
    public void testGetDoc() throws IOException {
        GetRequest getRequest = new GetRequest("nan_index");
        getRequest.id("1");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse);
    }


    // 测试文档删除
    @Test
    public void testDelDoc() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("nan_index","2");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(deleteResponse);
    }

    // 测试文档的更新(id存在就是更新,id不存在就是添加)(用下面这个方法更新时，是全局更新，就是说里面的字段全部被覆盖
    // 新对象里没有的字段就没有了)
    @Test
    public void testUpdateDoc() throws IOException {
        User user = User.builder()
                .name("xiong")
                .age(18)
                .build();
        IndexRequest indexRequest = new IndexRequest("nan_index");
        indexRequest.id("2");
        indexRequest.source(JSON.toJSONString(user),XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse);
    }

    // 测试文档的更新(id存在就是更新,id不存在就是添加)
    // (用下面这个方法更新时，是局部更新，就是说只会覆盖其中有的字段)
    @Test
    public void testUpdateBetterDoc() throws IOException {
        //准备好修改的数据
        User user = User.builder()
                .name("xiaoer")
                .age(18)
                .build();

        // 创建更新请求
        UpdateRequest updateRequest = new UpdateRequest("nan_index", "1");
        // 把要更新的数据装进去
        updateRequest.doc(JSON.toJSONString(user),XContentType.JSON);
        // 执行更新语句
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(updateResponse);
    }


    // 测试文档批量添加（添加会了，批量删除、更新、修改是一样的）
    @Test
    public void testBulkAdd() throws IOException {
        // 准备要添加的数据
        List<User> users = new ArrayList<>();
        users.add(new User("xiaofei",25,"每天都是正能量",new String[]{"加油","早起晚睡"}));
        users.add(new User("xiaohua",22,"xiaohua",new String[]{"html","早起晚睡"}));
        users.add(new User("xiaoer",23,"我是菜鸟",new String[]{"宅男","早起晚睡"}));
        users.add(new User("xiaoge",22,"一看工资2500",new String[]{"加油","渣男"}));
        users.add(new User("xiaomei",23,"给我钱就行",new String[]{"hh","早起晚睡"}));


        // 创建批量请求
        BulkRequest bulkRequest = new BulkRequest();
        // 利用循环将每一个add请求添加到bulkRequest请求中
        for (int i = 0; i < users.size(); i++) {
            IndexRequest indexRequest = new IndexRequest("nan_index").id(""+i);
            indexRequest.source(JSON.toJSONString(users.get(i)),XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        // 执行批量请求
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse);
    }


    // 测试文档列表的查询（带条件）
    // 这是ElasticSearch最重要的地方
    @Test
    public void testGetListDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("nan_index");
        // 构建搜索builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建查询条件(查询所有)
        //  MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        // 构建查询条件(精确匹配)
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("name", "xiaomei");

        // 把查询条件设置给搜索builder
        searchSourceBuilder.query(termsQueryBuilder);

        // 设置分页查询(跟sql语句的limit一样)
        searchSourceBuilder.from(0); // 开始下标(当前页码-1)*每页显示条数
        searchSourceBuilder.size(3); // 要查多少个

        // 设置排序规则
        searchSourceBuilder.sort("age", SortOrder.DESC);

        // 把所有条件设置给查询请求
        searchRequest.source(searchSourceBuilder);

        // 开始查询
        SearchResponse searchResponse =
                restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 查看结果(结果很多，所以循环)
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit: hits) {
            System.out.println(hit.getSourceAsString());
        }

    }


    // 测试过滤查询出来的字段（也就是当我们不想把表中所有的字段查出来）
    @Test
    public void testFilterDoc() throws IOException {
        // 构建搜索查询请求
        SearchRequest searchRequest = new SearchRequest().indices("nan_index");

        // 构建查询条件builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建真正的查询条件（这里是全部查询）
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

        // 把查询条件设置给builder
        searchSourceBuilder.query(matchAllQueryBuilder);

        // 设置过滤字段
        String[] excludes = {};
        String[] includes = {"name"};
        searchSourceBuilder.fetchSource(includes,excludes);

        // 把所有的查询条件builder设置给查询请求
        searchRequest.source(searchSourceBuilder);

        //执行请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //打印结果
        SearchHits hits = searchResponse.getHits();
        for (SearchHit hit:hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    // 多条件查询，也叫组合查询
    @Test
    public void testBoolqueryDoc() throws IOException {
        // 构建查询请求
        SearchRequest searchRequest = new SearchRequest("nan_index");

        // 构建搜索条件builder
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建多条件builder
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        // 在多条件builder中设置满足条件
        // must 就是必须满足这个条件(相当于mysql中column = 值)
        // mustnot 就是必须不满足这个条件（相当于mysql中column！=值）
        // should 就是或者的意思（相当于mysql中的or）
        boolQueryBuilder.must(QueryBuilders.termsQuery("name","xiaofei"));
//        boolQueryBuilder.must(QueryBuilders.termsQuery("age","24"));
        boolQueryBuilder.should(QueryBuilders.termsQuery("age","24"));
        boolQueryBuilder.should(QueryBuilders.termsQuery("age","28"));
        // 把多条件查询条件放到builder中
        searchSourceBuilder.query(boolQueryBuilder);

        // 把所有搜索条件设置到查询请求中
        searchRequest.source(searchSourceBuilder);
        // 执行请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 打印结果
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit :hits){
            System.out.println(hit.getSourceAsString());
        }
    }


    // 范围查询
    @Test
    public void testRangeDoc() throws IOException {
        // 构建查询请求
        SearchRequest searchRequest = new SearchRequest("nan_index");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置到范围的字段
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("age");
        // 设置范围（）gte就是大于等于
        rangeQueryBuilder.gte(24);
        rangeQueryBuilder.lte(30);
        // 把范围查询设置到条件中
        searchSourceBuilder.query(rangeQueryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit :hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    // 模糊查询
    @Test
    public void testLikeDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("nan_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "xiao").fuzziness(Fuzziness.TWO);
        searchSourceBuilder.query(fuzzyQueryBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit :hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    //高亮查询
    @Test
    public void testHighLightDoc() throws IOException {
        // 构建搜索请求
        SearchRequest searchRequest = new SearchRequest("nan_index");

        // 构建搜索条件构造器(也就是总的搜索条件)
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        // 构建单独的一个高亮构建器
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        // 设置高亮字段
        highlightBuilder.preTags("<font color='red'>"); //前缀
        highlightBuilder.postTags("</font>");   // 后缀
        highlightBuilder.field("name");
        // 把单独的高亮构建器设置给总构建器
        searchSourceBuilder.highlighter(highlightBuilder);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // 把总的搜索条件给到搜索请求中
        searchRequest.source(searchSourceBuilder);
        // 执行请求
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        // 打印结果
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit :hits){
            System.out.println(hit.getSourceAsString());
            // 获取对应的高亮域
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            System.out.println(highlightFields);
            // 获取对应的高亮字段
            HighlightField highlightField = highlightFields.get("name");
            if(highlightField != null) {
                // 拿到高亮字段的文本域
                Text[] texts = highlightField.getFragments();
                String name = "";
                for (Text text : texts) {
                    name += text;
                    // 打印高亮字段
                    System.out.println(name);
                }
            }
        }
    }

    // 最大值、平均值、最小值
    @Test
    public void testAggraDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("nan_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建一个最大值builder
        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("MAXAGE").field("age");
        // 把最大值builder设置给总查询条件
        searchSourceBuilder.aggregation(maxAggregationBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit : hits){
            System.out.println(hit.getSourceAsString());
        }
    }

    // 分组查询
    @Test
    public void testAggraGroupDoc() throws IOException {
        SearchRequest searchRequest = new SearchRequest("nan_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 构建一个分组builder
        // terms里面的参数是给分组取的名字、后面field是要分组的字段
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("AGEGROUP").field("age");
        // 把分组builder设置给总查询条件
        searchSourceBuilder.aggregation(termsAggregationBuilder);

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        for(SearchHit hit : hits){
            System.out.println(hit.getSourceAsString());
        }
    }











}
