package com.leverx.project.service.impl;

import com.leverx.project.model.Role;
import com.leverx.project.model.User;
import com.leverx.project.repository.RoleDAO;
import com.leverx.project.repository.UserDAO;
import com.leverx.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final RoleDAO roleDAO;
    private final UserDAO userDAO;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(RoleDAO roleDAO, UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User create(User user, Integer roleId) {
        Optional<Role> optionalRole = roleDAO.findById(roleId);
        List<Role> roleList = new ArrayList<>();
        roleList.add(optionalRole.get());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleList);
        user.setCreated_at(LocalDateTime.now());
        log.info("IN UserServiceImpl create() user {}", user);
        return userDAO.save(user);
    }

    @Override
    public User update(Integer id, User user) {
        Optional<User> updatedUser = userDAO.findById(id);
        updatedUser.get().setPassword(user.getPassword());
        user.setFirst_name(updatedUser.get().getFirst_name());
        user.setLast_name(updatedUser.get().getLast_name());

        user.setEmail(updatedUser.get().getEmail());
        user.setCreated_at(updatedUser.get().getCreated_at());
        log.info("IN UserServiceImpl update() user with id {}, {}", id, user);
        return userDAO.save(user);
    }

    @Override
    public Optional<User> getById(Integer id) {
        log.info("IN UserServiceImpl getById() user with id {}", id);
        return userDAO.findById(id);
    }

    @Override
    public User getByEmail(String email) {
        log.info("IN UserServiceImpl getByEmail() user with email {}", email);
        return userDAO.findByEmail(email);
    }

    @Override
    public User getByEmailAndPassword(String email, String password) {
        User user = userDAO.findByEmailAndPassword(email, bCryptPasswordEncoder.encode(password));
        log.info("IN UserServiceImpl getByEmailAndPassword() user {} : ",user);

        if(user != null) {
            if(bCryptPasswordEncoder.encode(password).equals(user.getPassword())){
                return user;
            }
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        log.info("IN UserServiceImpl delete() user with id {}", id);
        userDAO.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        log.info("IN UserServiceImpl getAll() users List");
        return userDAO.findAll();
    }
}
