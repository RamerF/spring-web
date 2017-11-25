package org.ramer.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.ramer.demo.service.UserService;
import org.ramer.demo.util.EncryptUtil;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RAMER on 5/22/2017.
 */
@Service
@Slf4j
public class CustomUserService implements UserDetailsService{
    @Resource
    private UserService userService;
    private static final String[] PRIVILEGE_SUFFIXES = { "view", "edit" };

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        EncryptUtil.execEncrypt("ramer");
        org.ramer.demo.domain.User user = userService.getByName(username);
        log.debug("loadUserByUsername  username: [{}]", username);
        if (user == null) {
            log.debug("loadUserByUsername  username not exist: [{}]", username);
            throw new UsernameNotFoundException("username not found : " + username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            role.getPrivileges().forEach(privilege -> {
                String privilegeName = privilege.getName();
                if (privilegeName.contains("*")) {
                    authorities.add(new SimpleGrantedAuthority(privilegeName));
                    for (String privilegeSuffix : PRIVILEGE_SUFFIXES) {
                        authorities.add(new SimpleGrantedAuthority(
                                privilegeName.substring(0, privilegeName.indexOf("*")) + privilegeSuffix));
                    }
                } else {
                    authorities.add(new SimpleGrantedAuthority(privilegeName));
                }
            });
        });
        log.debug("loadUserByUsername permission : {}", authorities);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                authorities);
    }

}
