package org.ramer.demo.service;

import org.ramer.demo.domain.Roles;

/**
 * Created by RAMER on 6/6/2017.
 */
public interface RolesService{
    Roles saveOrUpdate(Roles roles);

    public Roles getByName(String name);

    long countRole();
}
