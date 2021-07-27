package com.sy.service.impl;

import com.sy.service.EsService;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.rest.RestChannel;
import org.elasticsearch.rest.action.RestToXContentListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class EsServiceImpl implements EsService {

    public static final Logger logger = LoggerFactory.getLogger(EsServiceImpl.class);

    @Autowired
    private RestHighLevelClient client;


    @Override
    public Boolean createIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest("age");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        XContentBuilder builder = XContentFactory.jsonBuilder()
                .startObject()
                .startObject("properties")
                .startObject("message")
                .field("type", "text")
                .endObject()
                .startObject("aaaaa")
                .field("type", "text")
                .endObject()
                .endObject()
                .endObject();

        request.mapping(builder.toString());
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        return acknowledged;
    }

    @Override
    public Boolean checkIndexExist(String name) throws IOException {
        GetIndexRequest request = new GetIndexRequest(name);
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    @Override
    public Boolean getMapping(String name) throws IOException {
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(name);

        GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
        Map<String, MappingMetadata> allMappings = response.mappings();
        MappingMetadata indexMapping = allMappings.get(name);
        Map<String, Object> mapping = indexMapping.sourceAsMap();
        logger.error(mapping.toString());
        return true;
    }
}
