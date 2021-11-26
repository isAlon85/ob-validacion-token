package com.team1.obvalidacion.controllers;

import com.team1.obvalidacion.entities.User;
import com.team1.obvalidacion.security.jwt.JwtTokenUtil;
import com.team1.obvalidacion.security.payload.JwtResponse;
import com.team1.obvalidacion.security.payload.LoginRequest;
import com.team1.obvalidacion.security.payload.MessageResponse;
import com.team1.obvalidacion.security.payload.RegisterRequest;
import com.team1.obvalidacion.services.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private final String ROOT = "/api";
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private final AuthenticationManager autenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserServiceImpl userService;


    public UserController(
            AuthenticationManager autenticationManager, JwtTokenUtil jwtTokenUtil,
            UserServiceImpl userService) {
        this.autenticationManager = autenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(ROOT + "/users")
    @ApiOperation("Find all Users in DB")
    public ResponseEntity<List<User>> findAll() {
        return userService.findAll();
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(ROOT + "/users/" + "{id}")
    @ApiOperation("Find a User in DB by ID")
    public ResponseEntity<User> findOneById(@PathVariable Long id) {
        return userService.findOneById(id);
    }

    @PostMapping(ROOT + "/auth/register")
    @ApiOperation("Register a User in DB with a JSON")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest signUpRequest) {
        ResponseEntity<MessageResponse> result = userService.register(signUpRequest);

        if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST))
            log.warn("Trying to create a User with ID or email/username is already used");

        return result;
    }

    @PostMapping(ROOT + "/auth/login")
    @ApiOperation("Login with a JSON")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        ResponseEntity<JwtResponse> result = userService.login(loginRequest);

        if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST))
            log.warn("Trying to create a User with ID or email/username is already used");

        return result;
    }

    @PatchMapping(ROOT + "/users/" + "{id}")
    @ApiOperation("Update a User in DB with a JSON")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody Map<Object, Object> fields) {
        ResponseEntity<User> result = userService.patch(id, fields);

        if (result.getStatusCode().equals(HttpStatus.BAD_REQUEST))
            log.warn("Trying to update a User without ID");

        if (result.getStatusCode().equals(HttpStatus.NOT_FOUND))
            log.warn("Trying to update a User with a non existing ID");

        return result;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(ROOT + "/users/" + "{id}")
    @ApiOperation("Delete a User in DB by ID")
    public ResponseEntity delete(@PathVariable Long id) {
        ResponseEntity result = userService.delete(id);

        if (result.getStatusCode().equals(HttpStatus.NOT_FOUND))
            log.warn("Trying to delete a User with a non existing ID");

        return result;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping(ROOT + "/users/" + "restartDB")
    public ResponseEntity deleteAll(@RequestHeader HttpHeaders headers) {
        ResponseEntity result = userService.deleteAll();

        if (result.getStatusCode().equals(HttpStatus.NOT_FOUND))
            log.warn("The DB is already empty");
        else
            log.warn("Deleting all by request of " + headers.get("User-Agent"));

        return result;
    }
}
