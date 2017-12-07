package org.ramer.demo.service.impl;


import javax.annotation.Resource;

import org.ramer.demo.domain.Roles;
import org.ramer.demo.repository.RolesRepository;
import org.ramer.demo.service.RolesService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by RAMER on 6/6/2017.
 */
@Service
public class RolesServicesImpl implements RolesService {
    @Resource
    private RolesRepository rolesRepository;

    @Transactional
    @Override
    public Roles saveOrUpdate(Roles roles) {
        return rolesRepository.saveAndFlush(roles);
    }

    @Transactional(readOnly = true)
    @Override
    public Roles getByName(String name) {
        return rolesRepository.getByName(name);
    }

    @Transactional(readOnly = true)
    @Override
    public long countRole() {
        return rolesRepository.count();
    }
}
