package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.User;
import com.team1.obvalidacion.repositories.UserRepository;
import com.team1.obvalidacion.security.jwt.JwtTokenUtil;
import com.team1.obvalidacion.security.payload.LoginRequest;
import com.team1.obvalidacion.security.payload.RegisterRequest;
import org.junit.jupiter.api.*;
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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Test
    @Order(1)
    void findAll() {
        assertEquals(HttpStatus.NOT_FOUND, userService.findAll().getStatusCode());

        RegisterRequest user6 = new RegisterRequest();
        user6.setEmail("admin@validation.com");
        user6.setName("Admin");
        user6.setSurname("Admin");
        user6.setPassword("Admin123");
        userService.register(user6);
        assertEquals(1, userService.findAll().getBody().size());
        assertEquals(false, userService.findAll().getBody().isEmpty());
        assertEquals(HttpStatus.OK, userService.findAll().getStatusCode());

    }

    @Test
    @Order(2)
    void findOneById() {
        Optional<User> user = userRepository.findByUsername("admin@validation.com");
        assertEquals(HttpStatus.OK, userService.findOneById(user.get().getId()).getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, userService.findOneById(9865L).getStatusCode());
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

        map.put("email", "novalido");
        assertEquals(HttpStatus.BAD_REQUEST, userService.patch(user.get().getId(), map).getStatusCode());
        assertEquals("mail@test.com", userService.findOneById(user.get().getId()).getBody().getUsername());
        assertEquals("mail@test.com", userService.findOneById(user.get().getId()).getBody().getEmail());
        map.clear();

        map.put("email", "cambiomail@test.com");
        assertEquals(HttpStatus.OK, userService.patch(user.get().getId(), map).getStatusCode());
        assertEquals("cambiomail@test.com", userService.findOneById(user.get().getId()).getBody().getEmail());
        assertEquals("cambiomail@test.com", userService.findOneById(user.get().getId()).getBody().getUsername());
        map.clear();

        map.put("name", "Perico");
        map.put("surname", "Delgado");
        assertEquals(HttpStatus.OK, userService.patch(user.get().getId(), map).getStatusCode());
        assertEquals("Perico", userService.findOneById(user.get().getId()).getBody().getName());
        assertEquals("Delgado", userService.findOneById(user.get().getId()).getBody().getSurname());
        map.clear();

        map.put("password", "Falsa123");
        assertEquals(HttpStatus.OK, userService.patch(user.get().getId(), map).getStatusCode());
        assertEquals(true, userService.findOneById(user.get().getId()).getBody().getPassword().startsWith("$2a",0));
        map.clear();

        map.put("rejected", true);
        map.put("validated", true);
        assertEquals(HttpStatus.OK, userService.patch(user.get().getId(), map).getStatusCode());
        assertEquals(false, userService.findOneById(user.get().getId()).getBody().isRejected());
        assertEquals(false, userService.findOneById(user.get().getId()).getBody().isValidated());
        map.clear();

        map.put("rejected", true);
        assertEquals(HttpStatus.OK, userService.patch(user.get().getId(), map).getStatusCode());
        assertEquals(false, userService.findOneById(user.get().getId()).getBody().isRejected());
        assertEquals(null, userService.findOneById(user.get().getId()).getBody().getFrontId());
        assertEquals(null, userService.findOneById(user.get().getId()).getBody().getBackId());
        assertEquals(false, userService.findOneById(user.get().getId()).getBody().isValidated());
        map.clear();

        map.put("validated", true);
        assertEquals(HttpStatus.OK, userService.patch(user.get().getId(), map).getStatusCode());
        assertEquals(true, userService.findOneById(user.get().getId()).getBody().isValidated());
        assertEquals(false, userService.findOneById(user.get().getId()).getBody().isRejected());
        map.clear();

        map.put("restarted", true);
        assertEquals(HttpStatus.OK, userService.patch(user.get().getId(), map).getStatusCode());
        assertEquals(false, userService.findOneById(user.get().getId()).getBody().isValidated());
        assertEquals(false, userService.findOneById(user.get().getId()).getBody().isRejected());
        assertEquals(false, userService.findOneById(user.get().getId()).getBody().isRestarted());
        map.clear();
    }

    @Test
    @Order(7)
    void delete() {
        Optional<User> user1 = userRepository.findByUsername("cambiomail@test.com");
        assertEquals(HttpStatus.NO_CONTENT, userService.delete(user1.get().getId()).getStatusCode());

        // Deleting twice
        assertEquals(HttpStatus.NOT_FOUND, userService.delete(user1.get().getId()).getStatusCode());

    }

    @Test
    @Order(6)
    void whoami() {
        // admin@validation.com Token valid until 2021/12/12
        String username = jwtTokenUtil.getUsernameFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkB2YWxpZGF0aW9uLmNvbSIsInJvbGVzIjoiUk9MRV9BRE1JTixST0xFX1VTRVIiLCJpYXQiOjE2Mzg3NjQyNzEsImV4cCI6MTYzOTM2OTA3MX0.tcvLIwtDk90DcSjhPHY3CbAkmUBAiTyt9kY2tXCspGI");
        assertEquals(HttpStatus.OK, userService.whoami(username).getStatusCode());

        // hombreinmortal@postman.com Token valid until 2021/12/12
        username = jwtTokenUtil.getUsernameFromToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJob21icmVpbm1vcnRhbEBwb3N0bWFuLmNvbSIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNjM4NzY0MzIzLCJleHAiOjE2MzkzNjkxMjN9.cLcgN-TKi7r1w5kvcvFaDN7JHiA7RLn8lZzELNkR4Yo");
        assertEquals(HttpStatus.NOT_FOUND, userService.whoami(username).getStatusCode());
    }

    @Test
    @Order(8)
    void deleteAll() {
        assertEquals(HttpStatus.NO_CONTENT, userService.deleteAll().getStatusCode());

        // Deleting Twice
        assertEquals(HttpStatus.NOT_FOUND, userService.deleteAll().getStatusCode());

    }

}