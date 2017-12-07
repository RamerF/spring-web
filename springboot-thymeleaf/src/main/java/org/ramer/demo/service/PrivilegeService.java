package org.ramer.demo.service;

import org.ramer.demo.domain.Privilege;

import java.util.List;

/**
 * Created by RAMER on 8/8/2017.
 */
public interface PrivilegeService{
    Privilege saveOrUpdate(Privilege privilege);

    boolean saveBatch(List<Privilege> privileges);
}
