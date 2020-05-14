package org.ramer.demo.service;

import org.ramer.demo.domain.User;
import org.springframework.data.domain.Page;

/**
 * The interface User service.
 */
public interface UserService {
  /**
   * Gets by id.
   *
   * @param id the id
   * @return the by id
   */
  User getById(Long id);

  /**
   * Save or update user.
   *
   * @param user the user
   * @return the user
   */
  User saveOrUpdate(User user);

  /**
   * Delete.
   *
   * @param id the id
   */
  void delete(Long id);

  /**
   * Delete.
   *
   * @param user the user
   */
  void delete(User user);

  /**
   * Gets user by page.
   *
   * @param page the page
   * @param size the size
   * @return the user by page
   */
  Page<User> getUserByPage(int page, int size);

  /**
   * Gets by name.
   *
   * @param username the username
   * @return the by name
   */
  User getByName(String username);
}
