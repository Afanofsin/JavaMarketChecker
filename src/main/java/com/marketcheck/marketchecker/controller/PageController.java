package com.marketcheck.marketchecker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("message", "Hello I dont know");
        return "base";
    }
}
