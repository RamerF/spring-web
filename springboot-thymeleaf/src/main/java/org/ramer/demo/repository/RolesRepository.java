/*
 *
 */
package org.ramer.demo.repository;

import org.ramer.demo.domain.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Roles repository.
 *
 * @author ramer
 */
@Repository
public interface RolesRepository extends JpaRepository<Roles, Integer>{

    Roles getByName(String name);
}
