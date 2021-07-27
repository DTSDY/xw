package com.sy.service;

import com.sy.bean.User;

import java.io.IOException;
import java.text.ParseException;

public interface SearchService {

    // 创建库表
    void createIndex() throws IOException;
    // 判断库表是否存在
    boolean existIndex(String indexName) throws IOException;
    // 添加数据
    void add(User json) throws IOException, ParseException;
    // 更新数据
    void update(User json) throws IOException;
    // 删除库表
    void deleteIndex(String index) throws Exception;
}
