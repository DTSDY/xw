package com.sy.controller;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/es")
public class ESController {

    public static final Logger logger = LoggerFactory.getLogger(ESController.class);

    @Autowired
    private RestHighLevelClient client;

    @GetMapping("/get")
    public Boolean getIndexIsExist(
//            @PathVariable("id") String id,
//            @PathVariable("name") String name
    ) throws IOException {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        getRequest.storedFields("_none_");
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        return exists;
    }

}
