package org.ramer.demo.repository;

import org.ramer.demo.domain.User;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    @Modifying
    @Query(value = "delete from User where id = :id")
    void delete(@Param("id") Integer id);
}
