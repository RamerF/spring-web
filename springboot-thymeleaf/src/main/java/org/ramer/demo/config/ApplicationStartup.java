package org.ramer.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.ramer.demo.domain.Privilege;
import org.ramer.demo.domain.Roles;
import org.ramer.demo.service.PrivilegeService;
import org.ramer.demo.service.RolesService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAMER on 8/29/2017.
 */
@Component
@Slf4j
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent>{
    @Resource
    private RolesService rolesService;
    @Resource
    private PrivilegeService privilegeService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (rolesService.countRole() < 1) {
            log.debug("onApplicationEvent ：init security");
            initRoleAndPrivilege();
        }
    }

    private void initRoleAndPrivilege() {
        // user role
        Roles roles = new Roles();
        roles.setName("ROLE_USER");
        List<Privilege> privileges = new ArrayList<>();
        Privilege userPrivilege = new Privilege();
        // the access privileges for user default
        userPrivilege.setName("user:*");
        privileges.add(userPrivilege);
        roles.setPrivileges(privileges);
        rolesService.saveOrUpdate(roles);
        privilegeService.saveBatch(privileges);
        // the access privileges for admin default
        roles = new Roles();
        roles.setName("ROLE_ADMIN");
        Privilege adminPrivilege = new Privilege();
        adminPrivilege.setName("global:*");
        privileges = new ArrayList<>();
        privileges.add(adminPrivilege);
        roles.setPrivileges(privileges);
        rolesService.saveOrUpdate(roles);
        privilegeService.saveBatch(privileges);
    }
}
