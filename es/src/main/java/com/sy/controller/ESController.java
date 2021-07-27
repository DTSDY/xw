package com.sy.controller;

import com.sy.service.EsService;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/es")
public class ESController {

    public static final Logger logger = LoggerFactory.getLogger(ESController.class);

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private EsService esService;

    // 测试连通
    @GetMapping("/check/{name}")
    public Boolean getIndexIsExist(
            @PathVariable("name") String name
    ) throws IOException {
        return esService.checkIndexExist(name);
    }

    @GetMapping("/create")
    public Boolean createIndex(
    ) throws IOException {
        return esService.createIndex();
    }

    @GetMapping("/getMapping/{name}")
    public Boolean createIndex(
            @PathVariable("name") String name
    ) throws IOException {
        return esService.getMapping(name);
    }

}
