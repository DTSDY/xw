package com.sy.service.impl;

import com.sy.bean.User;
import com.sy.constant.ESConstants;
import com.sy.service.SearchService;
import com.sy.utils.SearchUtil;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    public static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;

    @Override
    public void createIndex() throws IOException {
        if (!existIndex(ESConstants.ES_INDEX_TEST_STRING)) {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(ESConstants.ES_INDEX_TEST_STRING);
            createIndexRequest.settings(Settings.builder().put("number_of_replicas", 1).put("number_of_shards", 1).build());
            //使用工具类建立索引
            SearchUtil.buildMapping(createIndexRequest);
            CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } else {
            logger.error("创建失败,库已近存在");
        }
    }

    @Override
    public boolean existIndex(String indexName) throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(indexName);
        return client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
    }

    @Override
    public void add(User user) throws IOException {
        IndexRequest indexRequest = new IndexRequest(ESConstants.ES_INDEX_TEST_STRING);
        indexRequest.source(user, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        logger.error("插入数据结果是:{}", user.toString());
    }

    @Override
    public void update(User user) throws IOException {
        if (user.getUserId() != null) {
            // 通过msgId从ES中获得对象的ID(此ID为ES随机生成)
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            boolQueryBuilder.must(new TermQueryBuilder("userId", user.getUserId()));
            searchSourceBuilder.query(boolQueryBuilder);
            SearchRequest searchRequest = new SearchRequest(ESConstants.ES_INDEX_TEST_STRING);
            searchRequest.source(searchSourceBuilder);
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = response.getHits();
            String id = searchHits.getAt(0).getId();
            // 根据ID来更新 状态 注意在 状态报告中的 状态 和 下发日志中的状态的 字段名不同
            UpdateRequest updateRequest = new UpdateRequest(ESConstants.ES_INDEX_TEST_STRING, id);
            IndexRequest indexRequest = new IndexRequest(ESConstants.ES_INDEX_TEST_STRING, id);
            Map<Object, Object> source = new HashMap<>();
            source.put("userName", user.getUserName());
            source.put("address", user.getAddress());
            source.put("age", user.getAge());
            source.put("email", user.getEmail());
            source.put("phone", user.getPhone());
            source.put("sex", user.getSex());
            indexRequest.source(source);
            updateRequest.doc(indexRequest);
            UpdateResponse result = client.update(updateRequest, RequestOptions.DEFAULT);
            logger.error("更新了ES 中 userId " + user.getUserId() + " 的信息 " + user.toString());
        }
    }

    @Override
    public void deleteIndex(String index) throws Exception {
        if (existIndex(index)) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(index);
            AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            logger.error("删除index的结果是:{}", response);
        }
    }
}
