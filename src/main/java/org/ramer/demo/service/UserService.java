package org.ramer.demo.service;

import org.ramer.demo.domain.User;
import org.springframework.data.domain.Page;

public interface UserService{
    User getById(Integer id);

    User saveOrUpdate(User user);

    void delete(Integer id);

    void delete(User user);

    Page<User> getUserByPage(int page, int size);
}
