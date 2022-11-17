package com.panda.controller;

import com.panda.config.EsProperty;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private EsProperty esProperty;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @GetMapping("/test")
    public String test() {
        return esProperty.toString();
    }

    @PostMapping("/create/{index}")
    public CreateIndexResponse createIndex(@PathVariable("index") String index) throws IOException {

        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        return restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

    }




}
