package org.ramer.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.ramer.demo.domain.User;
import org.ramer.demo.repository.UserRepository;
import org.ramer.demo.service.UserService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Resource
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public User getById(Integer id) {
        return userRepository.findOne(id);
    }

    @Override
    public User saveOrUpdate(User user) {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.delete(id);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public Page<User> getUserByPage(int page, int size) {
        Sort sort = new Sort("id");
        Pageable pageable = new PageRequest(page, size, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    public User getByName(String username) {
        return userRepository.getByUsername(username);
    }
}
