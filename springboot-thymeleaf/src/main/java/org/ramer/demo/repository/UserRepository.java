package org.ramer.demo.repository;

import org.ramer.demo.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  /**
   * Delete.
   *
   * @param id the id
   */
  @Modifying
  @Query(value = "delete from User where id = :id")
  void delete(@Param("id") Long id);

  /**
   * Gets by username.
   *
   * @param username the username
   * @return the by username
   */
  User getByUsername(String username);
}
