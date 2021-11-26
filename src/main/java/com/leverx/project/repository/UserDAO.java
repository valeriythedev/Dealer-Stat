package com.leverx.project.repository;

import com.leverx.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    User findByEmailAndPassword(String email, String password);
}
