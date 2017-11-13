package org.ramer.demo.controller;

import org.ramer.demo.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CommonController{
    @GetMapping("/hello")
    public String sayHello(Map<String, Object> map) {
        User user = new User();
        user.setUsername("Tom");
        map.put("user", user);
        return "hello";
    }

}
