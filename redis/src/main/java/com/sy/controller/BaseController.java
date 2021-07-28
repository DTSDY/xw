package com.sy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping("/sea")
    public String test() {
        System.out.println("ssssssssssssssssssssss");
        return ";sea";
    }
}
