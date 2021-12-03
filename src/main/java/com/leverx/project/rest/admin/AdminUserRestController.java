package com.leverx.project.rest.admin;

import com.leverx.project.model.User;
import com.leverx.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/admin/users/",
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserRestController {

    private final UserService userService;

    @Autowired
    public AdminUserRestController(final UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Integer id) {
        List<User> userList = userService.getAll();
        if(userList.stream().noneMatch(p -> p.getId().equals(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getById(@PathVariable("id") Integer id) {
        List<User> userList = userService.getAll();
        if(userList.stream().noneMatch(p -> p.getId().equals(id))) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> optionalUser = userService.getById(id);
        return new ResponseEntity<>(optionalUser, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<User>> getAll() {
        List<User> usersList = userService.getAll();
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
}
