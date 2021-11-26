package com.leverx.project.service;

import com.leverx.project.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(User user, Integer roleId);

    User update(Integer id, User user);

    Optional<User> getById(Integer id);

    User getByEmail(String email);

    User getByEmailAndPassword(String email, String password);

    void delete(Integer id);

    List<User> getAll();
}
