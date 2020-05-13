package com.personal.ESService;

import com.alibaba.fastjson.JSONObject;
import com.personal.pojo.Position;
import com.personal.pojo.web.ReleasePosition;
import com.personal.util.ConstPool;
import com.personal.util.Util;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**https://blog.csdn.net/qq_32123821/article/details/97395023?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
 * https://www.cnblogs.com/java-spring/p/11721615.html
 * https://blog.csdn.net/Amor_Leo/article/details/101018008
 * https://www.cnblogs.com/betterwgo/p/11268869.html
 * https://blog.csdn.net/truong/article/details/62046063
 * https://blog.csdn.net/paditang/article/details/78802799
 * [已过时]public interface PositionRepository extends ElasticsearchRepository<Position, Long>
 * ES工具类
 *
 * @author 李箎
 */
@Component
public class ElasticSearchUtil {
    //！！！
//    @Resource
    @Autowired
    private RestHighLevelClient client;

    /**
     * 返回实例
     * @return RestHighLevelClient
     * @throws Exception 异常信息
     */
    public RestHighLevelClient getObject() throws Exception {
        return this.client;
    }

    /**
     * 反射
     *
     * @return RestHighLevelClient.class
     */
    public Class<?> getObjectType() {
        return RestHighLevelClient.class;
    }

    /**
     * 客户端是否单例
     * @return true
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * 客户端实例的销毁
     * @throws Exception 异常信息
     */
    public void destroy() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    /**创建对象
     * 注入参数 参数设置之后 new一个高级别客户端实例
     * <li>Description: 自定义的构造方法 </li>
     *
     * @return RestHighLevelClient
     */
    private RestHighLevelClient buildClient() {
        try {
            //这里的builder方法有两个方式,第一个是传入Node(包含了多个节点,需要密码这些,我们没有配置,就暂时不需要),第二个就是传入HttpHost
            client = new RestHighLevelClient(RestClient.builder(new HttpHost(ConstPool.HOST, ConstPool.ES_PORT, ConstPool.HTTP_SCHEME)));
        } catch (Exception e) {
        }
        return client;
    }

    private Map<String, Object> query(String indexId) throws IOException {
        GetRequest request = new GetRequest(ConstPool.INDEX_NAME, indexId);
        GetResponse response = client.get(request, RequestOptions.DEFAULT);
        return response.getSource();
    }

    /**ok
     * 创建索引、配置映射，类似建库
     */
    public String createIndex(String indexName) {
        try {
            this.buildClient();
            if (this.isIndexExists(indexName)) {
                return "索引 " + indexName + " 已经存在";
            }
            //创建索引请求
            CreateIndexRequest request = new CreateIndexRequest(indexName);
            //索引设置
            request.settings(Settings.builder()
                    .put("index.number_of_shards", 3)
                    .put("index.number_of_replicas", 2)
            );
            //映射表 (索引)类型(mysql表)：position ES7不支持索引类型：https://blog.csdn.net/u014646662/article/details/94718834
            XContentBuilder builder = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("properties")
                    .startObject()
                    .field("id").startObject().field("index", "true").field("type", "long").endObject()
                    .field("name").startObject().field("index", "true").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .field("companyId").startObject().field("index", "true").field("type", "long").endObject()
                    .field("userId").startObject().field("index", "true").field("type", "long").endObject()
                    .field("detail").startObject().field("index", "true").field("type", "text").endObject()
                    .field("city").startObject().field("index", "true").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .field("needNum").startObject().field("index", "true").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .field("workExp").startObject().field("index", "true").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .field("eduBg").startObject().field("index", "true").field("type", "text").field("analyzer", "ik_max_word").endObject()
                    .field("salary").startObject().field("index", "true").field("type", "integer").endObject()
                    .field("salaryFloat").startObject().field("index", "true").field("type", "integer").endObject()
                    .field("worktype").startObject().field("index", "true").field("type", "text").endObject()
                    .field("faceto").startObject().field("index", "true").field("type", "text").endObject()
                    .field("status").startObject().field("index", "true").field("type", "integer").endObject()
                    .field("releaseTime").startObject().field("index", "true").field("type", "text").endObject()
                    .endObject()
                    .endObject();
//            PutMappingRequest mappingRequest = Requests.putMappingRequest(ConstPool.INDEX_NAME).type("position").source(builder);
            request.mapping(builder);
//            request.alias(new Alias(indexName + "_alias").filter(QueryBuilders.termQuery("", "")));
            //超时
            request.setTimeout(TimeValue.timeValueMinutes(2));
            //连接主节点超时
            request.setMasterTimeout(TimeValue.timeValueMinutes(1));
            //同步执行请求
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            //响应
            boolean acknowledged = response.isAcknowledged();
            boolean shardsAcknowledged = response.isShardsAcknowledged();
            if (acknowledged) {
                return "创建索引成功";
            }
            return "失败";
        } catch (Exception e1) {
            e1.printStackTrace();
            return "创建索引失败";
        } finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**ok
     * 删除索引
     *
     * @param indexName
     */
    public String delIndex(String indexName) {
        boolean acknowledged = false;
        try {
            this.buildClient();
            if (!this.isIndexExists(indexName)) {
                return "失败，索引 " + indexName + " 不存在";
            }
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(indexName);
            deleteIndexRequest.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);
            AcknowledgedResponse delete = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            acknowledged = delete.isAcknowledged();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return acknowledged ? "成功" : "失败";
    }

    /**ok
     * 判断索引是否存在
     *
     * @param indexName
     * @return
     */
    public boolean isIndexExists(String indexName) {
        boolean exists = false;
        try {
            GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
            getIndexRequest.humanReadable(true);
            exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**ok
     * ES type 增一行
     *
     * @param position
     * @return
     */
    public String savePosition(Position position, String indexName) {
        IndexRequest indexRequest = new IndexRequest(indexName);
        String positionJson = JSONObject.toJSONString(position);
        indexRequest.source(positionJson, XContentType.JSON);
        try {
            this.buildClient();
            if (!this.isIndexExists(indexName)) {
                return "失败";
            }
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            if (indexResponse != null) {
                String id = indexResponse.getId();
                String index = indexResponse.getIndex();
                long version = indexResponse.getVersion();
                //log.info("index:{},id:{}", index, id);
                if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
                    System.out.println("添加职位同步至ES成功\tindex：" + index + "\tid：" + id + "\tversion：" + version);
                    return ConstPool.ADD_POSITION_SUCCESS;
                } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                    System.out.println("修改职位同步至ES成功");
                    return ConstPool.ADD_POSITION_SUCCESS;
                }
                // 分片处理信息
                ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
                if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
                    System.out.println("分片处理信息.....");
                }
                // 如果有分片副本失败，可以获得失败原因信息
                if (shardInfo.getFailed() > 0) {
                    for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                        String reason = failure.reason();
                        System.out.println("副本失败原因：" + reason);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**ok
     * 删除职位
     *
     * @param id ES id
     * @param indexName
     * @return
     */
    public String delPosition(String id, String indexName) {
        DeleteRequest deleteRequest = new DeleteRequest(indexName);
        deleteRequest.id(id);
        try {
            this.buildClient();
            DeleteResponse deleteResponse = client.delete(deleteRequest, RequestOptions.DEFAULT);
            if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
                return "ES删除职位失败";
            } else {
                return "ES删除职位成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ES删除职位异常";
        }
        finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ok
     * 更新职位 修改一个或多个字段
     *
     * @param id
     * @param map position实体
     * @return
     */
    public String updatePosition(String id, Map<String, Object> map) {
        UpdateRequest updateRequest = new UpdateRequest(ConstPool.INDEX_NAME, id);
        updateRequest.doc(map);
        try {
            this.buildClient();
            UpdateResponse updateResponse = client.update(updateRequest, RequestOptions.DEFAULT);
            if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                return "ES更新职位成功";
            } else {
                return "ES更新职位失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ES更新职位异常";
        } finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**想查有没有该职位名称并且该公司id的 应该是没有不能命中 但是仍然命中，这是因为分词查询的坑！！！
     * 所以使用完全匹配
     * 能不能命中的问题！！！
     * 不该命中的命中了，该命中的没命中，命中有误！！！要注意这种命中不准确的问题
     * https://blog.csdn.net/weixin_36666151/article/details/100536527
     * ok
     *
     * @param name 职位名称
     * @return ES 数据id
     */
    public String getPositionByName(String name, long companyId) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.fetchSource(new String[]{"name", "companyId"}, new String[]{});
        //单个匹配 完全匹配 精准查询 数据类型也精准区分！！！
        MatchPhraseQueryBuilder name1 = matchPhraseQuery("name", name);
        MatchPhraseQueryBuilder companyId1 = matchPhraseQuery("companyId", companyId);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(companyId1).must(name1);
        sourceBuilder.query(boolBuilder);
        SearchRequest searchRequest = new SearchRequest(ConstPool.INDEX_NAME);
        searchRequest.source(sourceBuilder);
        try {
            this.buildClient();
            if (!this.isIndexExists(ConstPool.INDEX_NAME)) {
                return "";
            }
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(response);
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                return hit.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 通过id查找职位
     * @param id 职位id MySQL主键 long类型
     * @return ES中主键id String类型
     */
    public String getPositionById(long id) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.fetchSource(new String[]{"id"}, new String[]{});
        //单个匹配 完全匹配 精准查询 数据类型也精准区分！！！
        MatchPhraseQueryBuilder positionId = matchPhraseQuery("id", id);
        BoolQueryBuilder boolBuilder = QueryBuilders.boolQuery();
        boolBuilder.must(positionId);
        sourceBuilder.query(boolBuilder);
        SearchRequest searchRequest = new SearchRequest(ConstPool.INDEX_NAME);
        searchRequest.source(sourceBuilder);
        try {
            this.buildClient();
            if (!this.isIndexExists(ConstPool.INDEX_NAME)) {
                return "";
            }
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            System.out.println(response);
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                return hit.getId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**ok
     * 招聘者的搜索
     * 按职位名称搜索职位
     * ES普通搜索示例
     *
     * @param map
     * @return
     */
    public List<Position> getPositionList(Map<String, Object> map) {
        //搜索内容
        String searchContent = map.get("searchContent").toString();
        //公司id
        String companyId = map.get("companyId").toString();
        //返回的结果
        ArrayList<Position> positionList = new ArrayList<>();
        //创建搜索请求 全量搜索
        SearchRequest searchRequest = new SearchRequest(ConstPool.INDEX_NAME);
        //创建搜索构建者
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //自定义组合查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //如果用name直接查询，其实是匹配name分词过后的索引查到的记录(倒排索引)；如果用name.keyword查询则是不分词的查询，正常查询到的记录
        //范围查询
        //RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("releaseTime").from("2020-01-01").to("2020-10-10").format("yyyy-MM-dd");
        //精准查询
        //TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name.keyword", name);
        //前缀查询
        //PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("name.keyword", "前");
        //通配符查询
        //WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery("name.keyword", "*三");
        //模糊查询
        //FuzzyQueryBuilder fuzzyQueryBuilder = QueryBuilders.fuzzyQuery("name", "三");
        //单个匹配 模糊匹配
        MatchQueryBuilder matchQueryBuilder = matchQuery("name", searchContent);
        MatchQueryBuilder matchQueryBuilder2 = matchQuery("companyId", companyId);
        //按照月薪排序
        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("salary");
        //从小到大排序
        fieldSortBuilder.sortMode(SortMode.MIN);
        //must 与and    must not 非    should 或or
        boolQueryBuilder.must(matchQueryBuilder).must(matchQueryBuilder2);
        //设置构建搜索属性 多条件查询
        sourceBuilder.query(boolQueryBuilder).sort(fieldSortBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //传入构建进行搜索
        searchRequest.source(sourceBuilder);
        try {
            this.buildClient();
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            //处理结果
            RestStatus restStatus = response.status();
            if (restStatus != RestStatus.OK) {
                return new ArrayList<>();
            }
            //搜索命中
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                Position position = (Position) Util.jsonToObject(new Position(), sourceAsString);
                positionList.add(position);
            }
            return positionList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 求职者的搜索
     * @param searchContent 搜索内容
     * @return
     */
    public List<ReleasePosition> getReleasePositionList(String searchContent) {
        //返回的结果
        ArrayList<ReleasePosition> positionList = new ArrayList<>();
        //创建搜索请求 全量搜索
        SearchRequest searchRequest = new SearchRequest(ConstPool.INDEX_NAME);
        //创建搜索构建者
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        //自定义组合查询
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        MatchQueryBuilder matchQueryBuilder = matchQuery("name", searchContent);
        //按照月薪排序
        FieldSortBuilder fieldSortBuilder = SortBuilders.fieldSort("salary");
        //从小到大排序
        fieldSortBuilder.sortMode(SortMode.MIN);
        //must 与and    must not 非    should 或or
        boolQueryBuilder.must(matchQueryBuilder);
        //设置构建搜索属性 多条件查询
        sourceBuilder.query(boolQueryBuilder).sort(fieldSortBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //传入构建进行搜索
        searchRequest.source(sourceBuilder);
        try {
            this.buildClient();
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            //处理结果
            RestStatus restStatus = response.status();
            if (restStatus != RestStatus.OK) {
                return new ArrayList<>();
            }
            //搜索命中
            SearchHits hits = response.getHits();
            for (SearchHit hit : hits) {
                String sourceAsString = hit.getSourceAsString();
                ReleasePosition position = (ReleasePosition) Util.jsonToObject(new ReleasePosition(), sourceAsString);
                positionList.add(position);
            }
            return positionList;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 聚合查询
     * @param searchContent
     * @return
     */
    public Map<Integer, Long> aggregateQuery(String searchContent) {
        SearchRequest searchRequest = new SearchRequest(ConstPool.INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("by_salary").field("salary");
        sourceBuilder.aggregation(termsAggregationBuilder);

        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);

        try {
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = searchResponse.getAggregations();
            Map<String, Aggregation> stringAggregationMap = aggregations.asMap();
            ParsedLongTerms parsedLongTerms = (ParsedLongTerms) stringAggregationMap.get("by_salary");
            List<? extends Terms.Bucket> buckets = parsedLongTerms.getBuckets();
            Map<Integer, Long> map = new HashMap<>();
            for (Terms.Bucket bucket : buckets) {
                long docCount = bucket.getDocCount();//个数
                Number keyAsNumber = bucket.getKeyAsNumber();//年龄
                System.err.println(keyAsNumber + "岁的有" + docCount + "个");
                map.put(keyAsNumber.intValue(), docCount);
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 分页搜索
     *
     * @param searchContent
     * @return
     */
    public List<Position> pageSearch(String searchContent) {
        SearchRequest searchRequest = new SearchRequest(ConstPool.INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", searchContent));
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(1);
        searchSourceBuilder.timeout(new TimeValue(1000));
        searchSourceBuilder.trackTotalHits(true);
        searchRequest.source(searchSourceBuilder);
        try {
            this.buildClient();
            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] searchHits = searchResponse.getHits().getHits();
            while (searchHits != null && searchHits.length > 0) {
                searchHits = searchResponse.getHits().getHits();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                this.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }

    /**
     * 分页搜索2
     *
     * @param searchContent
     * @return
     */
    public List<Position> pageSearch2(String searchContent) {
        try {
            this.buildClient();
            BoolQueryBuilder boolQuery = new BoolQueryBuilder();
            //大于等于
            RangeQueryBuilder rangeQuery= QueryBuilders.rangeQuery("name").gte(8);
            boolQuery.filter(rangeQuery);
            MatchQueryBuilder matchQuery = new MatchQueryBuilder("name", searchContent);
            boolQuery.must(matchQuery);
            SearchResponse response = client.search(new SearchRequest(ConstPool.INDEX_NAME)
                    .source(new SearchSourceBuilder()
                            .query(boolQuery)
                            .from(0)
                            .size(2)
                            .trackTotalHits(true)
                    ), RequestOptions.DEFAULT);
            System.out.println(response.getHits().getTotalHits());
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new ArrayList<>();
    }
}
