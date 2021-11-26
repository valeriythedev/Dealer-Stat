package com.leverx.project.security;

import com.leverx.project.model.User;
import com.leverx.project.security.jwt.JwtUser;
import com.leverx.project.security.jwt.JwtUserFactory;
import com.leverx.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.getByEmail(s);

        if(user == null) {
            throw new UsernameNotFoundException("User with email: " + s + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        log.info("IN JwtUserDetailsService loadUserByUsername - user with email: {} successfully loaded", s);
        return jwtUser;
    }
}
