package org.ramer.demo.controller;

import org.ramer.demo.domain.Address;
import org.ramer.demo.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CommonController{
    @GetMapping("/hello")
    public String sayHello(Map<String, Object> map, HttpSession session) {
        User user = new User();
        user.setUsername("Tom");
        map.put("user", user);

        Address address = new Address();
        address.setId(1);
        address.setLocation("sc");
        address.setUser(user);
        map.put("address", address);
        user.setAddresses(new ArrayList() {
            {
                this.add(address);
            }
        });
        session.setAttribute("usr", user);

        List<User> users = new ArrayList<>(4);
        users.add(new User(1, "Tom", "Tom", new Date()));
        users.add(new User(2, "Jerry", "Jerry", new Date()));
        users.add(new User(3, "Mark", "Mark", new Date()));
        users.add(new User(4, "Twitter", "Twitter", new Date()));
        map.put("users", users);
        return "hello";
    }

}
