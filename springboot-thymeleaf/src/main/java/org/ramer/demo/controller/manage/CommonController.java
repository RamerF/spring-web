package org.ramer.demo.controller.manage;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("manageController")
@RequestMapping("/manage")
@PreAuthorize("hasRole('ADMIN')")
public class CommonController{

    @RequestMapping
    public String index() {
        return "/manage/manage_index";
    }

}
