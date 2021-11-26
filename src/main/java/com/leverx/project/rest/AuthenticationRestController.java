package com.leverx.project.rest;

import com.leverx.project.dto.AuthenticationRequest;
import com.leverx.project.model.User;
import com.leverx.project.security.jwt.JwtTokenProvider;
import com.leverx.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/auth/",
produces = MediaType.APPLICATION_JSON_VALUE,
consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/register/{roleId}")
    public ResponseEntity<User> register(@RequestBody User user, @PathVariable Integer roleId) {
        userService.create(user, roleId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationRequest authenticationRequest) {
        User user = userService.getByEmail(authenticationRequest.getEmail());

        if(user == null) {
            throw new UsernameNotFoundException("User with email: "+ authenticationRequest.getEmail() + " and password "+ authenticationRequest.getPassword() +" not found!");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        String token = jwtTokenProvider.createToken(authenticationRequest.getEmail(), user.getRoles());

        Map<Object, Object> response = new HashMap<>();
        response.put("email", authenticationRequest.getEmail());
        response.put("token", token);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}