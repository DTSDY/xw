package com.sy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("CACHE-SERVICE")
public interface CacheFeign {

    @GetMapping("/setStr/{key}/{value}")
    Boolean setString(@PathVariable String key, @PathVariable String value);

}
