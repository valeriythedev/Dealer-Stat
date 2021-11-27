package com.leverx.project.rest.admin;

import com.leverx.project.model.User;
import com.leverx.project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/admin/users/",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminUserController(final UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/{roleId}")
    public ResponseEntity<User> create(@RequestBody User user, @PathVariable("roleId") Integer roleId) {

        if(roleId > 3 || roleId < 1 || user == null) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }

        userServiceImpl.create(user, roleId);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Integer id, @RequestBody User user) {

        List<User> userList = userServiceImpl.getAll();

        if(userList.stream().noneMatch(p -> p.getId().equals(id))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        User updatedUser = userServiceImpl.update(id,user);

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {

        List<User> userList = userServiceImpl.getAll();

        if(userList.stream().noneMatch(p -> p.getId().equals(id))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        userServiceImpl.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable("id") Integer id) {

        List<User> userList = userServiceImpl.getAll();

        if(userList.stream().noneMatch(p -> p.getId().equals(id))) { return new ResponseEntity<>(HttpStatus.NOT_FOUND); }

        Optional<User> optionalUser = userServiceImpl.getById(id);

        return new ResponseEntity<>(optionalUser, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        List<User> usersList = userServiceImpl.getAll();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
}
