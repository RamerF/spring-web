package org.ramer.demo.repository;

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
public class TestUserRepository{
    @Resource
    private UserRepository userRepository;

    @Test
    public void testSave() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        user.setCreateTime(new Date());
        userRepository.saveAndFlush(user);
        Assert.assertThat(user.getId(), Matchers.greaterThan(0));
    }
}
