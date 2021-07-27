package com.sy.service.impl;

import com.sy.bean.User;
import com.sy.constant.ESConstants;
import com.sy.service.SearchService;
import com.sy.utils.SearchUtil;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
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
    public void update(User json) throws IOException {

    }

    @Override
    public void deleteIndex(String index) throws Exception {

    }
}
