package org.ramer.demo.service.impl;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.ramer.demo.domain.User;
import org.ramer.demo.repository.UserRepository;
import org.ramer.demo.service.UserService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
  @Resource
  private UserRepository repository;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public User getById(Long id) {
    return repository.findById(id).orElse(null);
  }

  @Override
  public User saveOrUpdate(User user) {
    return repository.saveAndFlush(user);
  }

  @Override
  public void delete(Long id) {
    repository.delete(id);
  }

  @Override
  public void delete(User user) {
    repository.delete(user);
  }

  @Override
  public Page<User> getUserByPage(int page, int size) {
    return repository.findAll(PageRequest.of(page, size, Sort.by("id")));
  }

  @Override
  public User getByName(String username) {
    return repository.getByUsername(username);
  }
}
