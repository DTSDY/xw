package com.sy.utils;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;

import java.io.IOException;

public class SearchUtil {
    public static void buildMapping(CreateIndexRequest request) throws IOException {
        XContentBuilder builder = JsonXContent.contentBuilder()
                .startObject()
                .startObject("properties")
                .startObject("userId")
                .field("type", "long")
                .field("index", "true")
                .endObject()
                .startObject("userName")
                .field("type", "text")
                .field("index", "true")
                .field("analyzer", "ik_max_word")
                .endObject()
                .startObject("phone")
                .field("type", "text")
                .field("index", "true")
                .endObject()
                .startObject("sex")
                .field("type", "text")
                .field("index", "true")
                .endObject()
                .startObject("age")
                .field("type", "int")
                .field("index", "true")
                .endObject()
                .startObject("address")
                .field("type", "text")
                .field("index", "true")
                .endObject()
                .startObject("email")
                .field("type", "text")
                .field("index", "true")
                .endObject()
                .endObject()
                .endObject();
        request.mapping(builder.toString());
    }
}
