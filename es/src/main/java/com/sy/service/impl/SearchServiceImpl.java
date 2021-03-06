package com.sy.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
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
            //???????????????????????????
            SearchUtil.buildMapping(createIndexRequest);
            CreateIndexResponse response = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        } else {
            logger.error("????????????,???????????????");
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
        String userJson = JSON.toJSONString(user);
        Map map = JSON.parseObject(userJson, Map.class);
        IndexRequest indexRequest = new IndexRequest(ESConstants.ES_INDEX_TEST_STRING);
        indexRequest.source(map, XContentType.JSON);
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        logger.error("?????????????????????:{}", user.toString());
    }

    @Override
    public void update(User user) throws IOException {
        if (user.getUserId() != null) {
            // ??????userId????????????????????????esId
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            boolQueryBuilder.must(new TermQueryBuilder("userId", user.getUserId()));
            searchSourceBuilder.query(boolQueryBuilder);
            SearchRequest searchRequest = new SearchRequest(ESConstants.ES_INDEX_TEST_STRING);
            searchRequest.source(searchSourceBuilder);
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits searchHits = response.getHits();
            String id = searchHits.getAt(0).getId();
            // ??????ID?????????
            UpdateRequest updateRequest = new UpdateRequest(ESConstants.ES_INDEX_TEST_STRING, id);
            IndexRequest indexRequest = new IndexRequest(ESConstants.ES_INDEX_TEST_STRING, id);
            Map<String, Object> source = new HashMap<>();
            source.put("userName", user.getUserName());
            source.put("address", user.getAddress());
            source.put("age", user.getAge());
            source.put("email", user.getEmail());
            source.put("phone", user.getPhone());
            source.put("sex", user.getSex());
            indexRequest.source(source);
            updateRequest.doc(indexRequest);
            UpdateResponse result = client.update(updateRequest, RequestOptions.DEFAULT);
            logger.error("?????????ES ??? userId " + user.getUserId() + " ????????? " + user.toString());
        }
    }

    @Override
    public void deleteIndex() throws Exception {
        if (existIndex(ESConstants.ES_INDEX_TEST_STRING)) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(ESConstants.ES_INDEX_TEST_STRING);
            AcknowledgedResponse response = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            logger.error("??????index????????????:{}", response);
        }
    }
}
