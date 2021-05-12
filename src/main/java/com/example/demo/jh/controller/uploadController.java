package com.example.demo.jh.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class uploadController {
    @GetMapping("/upload")
    public String  upload(){

        return "upload";
    }
}
