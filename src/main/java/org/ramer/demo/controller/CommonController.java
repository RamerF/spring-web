package org.ramer.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CommonController{
    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }
}
