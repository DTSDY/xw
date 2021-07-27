package com.sy.controller;

import com.sy.bean.User;
import com.sy.service.EsService;
import com.sy.service.SearchService;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequestMapping("/es")
public class ESController {

    public static final Logger logger = LoggerFactory.getLogger(ESController.class);

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private EsService esService;

    @Autowired
    private SearchService searchService;

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

    @GetMapping("/createIndex")
    public String createIndexByUtil() throws IOException {
        searchService.createIndex();
        return "创建成功";
    }

    @GetMapping("/delIndex")
    public String delIndex() throws Exception {
        searchService.deleteIndex();
        return "删除成功";
    }

    @PostMapping("/add")
    public String addUser(
            @RequestBody User user
    ) throws IOException, ParseException {
        searchService.add(user);
        return "创建成功";
    }

    @PostMapping("/update")
    public String updateUser(
            @RequestBody User user
    ) throws IOException {
        searchService.update(user);
        return "更新成功！";
    }
}
