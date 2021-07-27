package com.sy.service;

import java.io.IOException;

public interface EsService {
    Boolean createIndex() throws IOException;

    Boolean checkIndexExist(String name) throws IOException;

    Boolean getMapping(String name) throws IOException;
}
