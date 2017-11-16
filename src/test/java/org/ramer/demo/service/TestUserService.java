package org.ramer.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ramer.demo.domain.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TestUserService{
    @Resource
    private UserService userService;
    private static User user;
    static {
        user = new User();
        user.setUsername("ramer");
        user.setPassword("ramer");
        user.setCreateTime(new Date());
    }

    @Test
    public void testAddUser() {
        userService.saveOrUpdate(user);
        Assert.assertThat(user.getId(), Matchers.greaterThan(0));
    }

    @Test
    public void testGetById() {
        Assert.assertThat(userService.getById(user.getId()).getId(), Matchers.greaterThan(0));
    }

    @Test
    public void testDelete() throws Exception {
        userService.delete(user.getId());
        Assert.assertThat(userService.getById(user.getId()), Matchers.nullValue(User.class));
    }
}
