package org.ramer.demo.controller;

import io.swagger.annotations.*;
import org.ramer.demo.domain.*;
import org.ramer.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class CommonController{
    @Resource
    private UserService userService;

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
        map.put("users", userService.getUserByPage(0, 3));
        return "hello";
    }

    @PutMapping("/user/update/{userId}")
    @ResponseBody
    public CommonResponse updateUser(User user) {
        if (userService.saveOrUpdate(user).getId() > 0) {
            return new CommonResponse(true, "OK,success to update user info.");
        }
        return new CommonResponse(false, "Ops,something wrong.");
    }

    @PostMapping("/user/add")
    @ResponseBody
    public CommonResponse addUser(User user) {
        if (userService.saveOrUpdate(user).getId() > 0) {
            return new CommonResponse(true, "OK,success to add user info.");
        }
        return new CommonResponse(false, "Ops,something wrong.");
    }

    @DeleteMapping("/user/delete/{userId}")
    @ResponseBody
    public CommonResponse deleteUser(@PathVariable("userId") Integer userId) {
        userService.delete(userId);
        return new CommonResponse(true, "OK,success to dete user info.");
    }

    @GetMapping("/users")
    @ResponseBody
    @ApiOperation("get users page")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "You are not authorized access the resource"),
            @ApiResponse(code = 404, message = "The resource not found") })
    public Page<User> getUsersPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        return userService.getUserByPage(page, size);
    }

}
