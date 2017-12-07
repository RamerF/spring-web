package org.ramer.demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.ramer.demo.domain.*;
import org.ramer.demo.service.*;
import org.ramer.demo.util.EncryptUtil;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
public class CommonController{
    @Resource
    private UserService userService;
    @Resource
    private RolesService rolesService;
    @Resource
    private TopicService topicService;

    @GetMapping("/")
    public String demoList(Map<String, Object> map) {
        map.put("topics", topicService.getAll());
        return "demo_list";
    }

    @GetMapping("/preview")
    public String preview() {
        return "markdown_preview";
    }

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

    @PutMapping("/user/delete/{userId}")
    @ResponseBody
    public CommonResponse deleteUser(@PathVariable("userId") Integer userId) {
        userService.delete(userId);
        return new CommonResponse(true, "OK,success to dete user info.");
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/users")
    @ResponseBody
    @ApiOperation("get users page")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 401, message = "You are not authorized access the resource"),
            @ApiResponse(code = 404, message = "The resource not found") })
    public JSONObject getUsersPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<User> users = userService.getUserByPage(page, size);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("users", users);
        JSONObject usersJson = jsonObject.getJSONObject("users");
        JSONArray jsonArray = usersJson.getJSONArray("content");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject userJson = jsonArray.getJSONObject(i);
            Integer userid = userJson.getInteger("id");
            userJson.put("operator",
                    "<button class=\"md-btn md-flat-btn btnUpdate\" onclick=\"updateUser(this," + userid
                            + ")\">Update</button>"
                            + "<button class=\"md-btn md-flat-btn btnDelete\" onclick=\"deleteUser(this," + userid
                            + ")\">Delete</button>");
        }
        return usersJson;
    }

    @GetMapping("/login")
    public String input() {
        return "user_input";
    }

    @RequestMapping("/sign_in")
    @ResponseBody
    public CommonResponse userSignIn(HttpSession session, Principal principal) {
        if (principal == null) {
            return new CommonResponse(false, "Username or password not correct.");
        }
        User user = userService.getByName(principal.getName());
        session.setAttribute("user", user);
        SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if (savedRequest != null) {
            return new CommonResponse(true, savedRequest.getRedirectUrl());
        }
        return new CommonResponse(true, "/");
    }

    @PostMapping("/signup")
    @ResponseBody
    public CommonResponse userSignUp(User user) {
        log.debug(" userSignUp : [{}]", user);
        User byName = userService.getByName(user.getUsername());
        if (byName != null) {
            return new CommonResponse(false, "user exists. ");
        }
        user.setPassword(EncryptUtil.execEncrypt(user.getPassword()));
        List<Roles> roles = new ArrayList<>();
        Roles userRole = rolesService.getByName("ROLE_USER");
        log.debug(" userRole : {}", userRole);
        roles.add(userRole);
        user.setRoles(roles);
        userService.saveOrUpdate(user);
        if (user.getId() < 1) {
            return new CommonResponse(false, "Fail to sign up.");
        }
        return new CommonResponse(true, "sign up success.");
    }
}
