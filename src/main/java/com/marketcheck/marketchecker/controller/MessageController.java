package com.marketcheck.marketchecker.controller;

import com.marketcheck.marketchecker.domain.Message;
import com.marketcheck.marketchecker.domain.SkinService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/hello")
    public Message startMessage(@RequestParam(value="name", defaultValue="World")String name){
        return new Message(String.format("Hello %s!", name));
    }

    @GetMapping("/goodbye")
    public Message endMessage(@RequestParam(value="name", defaultValue="World")String name){
        return new Message(String.format("Goodbye %s!", name));
    }

}
