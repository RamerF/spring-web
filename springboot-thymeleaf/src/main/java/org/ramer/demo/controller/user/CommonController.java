package org.ramer.demo.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller("userController")
public class CommonController{
    @GetMapping("/user")
    public String userHome() {
        return "/user/user_home";
    }
}
