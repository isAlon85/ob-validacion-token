package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.User;
import com.team1.obvalidacion.repositories.UserRepository;
import com.team1.obvalidacion.security.payload.LoginRequest;
import com.team1.obvalidacion.security.payload.RegisterRequest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;


import java.io.IOException;
import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @Order(1)
    void findAll() {
        assertEquals(2, userRepository.count());
        assertEquals(HttpStatus.OK, userService.findAll().getStatusCode());
    }

    @Test
    @Order(2)
    void findOneById() {
        assertEquals("admin@validation.com", userService.findOneById(1L).getBody().getUsername());
        assertEquals(HttpStatus.OK, userService.findOneById(2L).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, userService.findOneById(10L).getStatusCode());
    }

    @Test
    @Order(3)
    void register() {
        RegisterRequest user3 = new RegisterRequest();
        user3.setEmail("mail@test.com");
        user3.setName("nombre1");
        user3.setSurname("apellido1");
        user3.setPassword("Test1234");

        RegisterRequest user4 = new RegisterRequest();
        user4.setEmail("mailnovalido");
        user4.setName("nombre1");
        user4.setSurname("apellido1");
        user4.setPassword("Test1234");

        RegisterRequest user5 = new RegisterRequest();
        user5.setEmail("mail@test.com");
        user5.setName("nombre1");
        user5.setSurname("apellido1");
        user5.setPassword("Test1234");

        assertEquals(HttpStatus.OK, userService.register(user3).getStatusCode());
        assertEquals(HttpStatus.BAD_REQUEST, userService.register(user4).getStatusCode());
        assertEquals(HttpStatus.CONFLICT, userService.register(user5).getStatusCode());
    }

    @Test
    @Order(4)
    void login() {
        LoginRequest login3 = new LoginRequest();
        login3.setPassword("Test1234");
        login3.setUsername("mail@test.com");

        try {
            assertEquals(HttpStatus.OK, userService.login(login3).getStatusCode());
        } catch (Exception e) {
            assertEquals("Bad credentials", e.getMessage());
        }

        login3.setPassword("Test1235");

        try {
            assertEquals(HttpStatus.OK, userService.login(login3).getStatusCode());
        } catch (Exception e) {
            assertEquals("Bad credentials", e.getMessage());
        }
    }

    @Test
    @Order(5)
    void patch() throws IOException {
        HashMap<Object, Object> map = new HashMap<Object, Object>();
        Optional<User> user = userRepository.findByUsername("mail@test.com");

        map.put("email", "cambiomail@test.com");
        assertEquals(HttpStatus.OK, userService.patch(user.get().getId(), map).getStatusCode());

        map.put("validated", true);
        assertEquals(HttpStatus.OK, userService.patch(user.get().getId(), map).getStatusCode());
    }

    @Test
    @Order(6)
    void delete() {
        Optional<User> user = userRepository.findByUsername("cambiomail@test.com");
        assertEquals(HttpStatus.NO_CONTENT, userService.delete(user.get().getId()).getStatusCode());

        assertEquals(HttpStatus.NOT_FOUND, userService.delete(user.get().getId()).getStatusCode());

    }

    @Test
    @Order(7)
    void whoami() {
        assertEquals(HttpStatus.NOT_FOUND, userService.whoami("usuarionologado").getStatusCode());
        assertEquals(HttpStatus.OK, userService.whoami("admin@validation.com").getStatusCode());
    }
}