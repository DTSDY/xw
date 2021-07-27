package com.sy.controller;

import com.sy.service.MQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MQService mqService;

    @GetMapping("send/{msg}")
    public Boolean sendMessageToMq(@PathVariable String msg){
        try {
            mqService.send(msg);
            return true;
        }catch (Exception e){
            return  false;
        }
    }
}
