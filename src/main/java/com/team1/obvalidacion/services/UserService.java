package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.User;
import com.team1.obvalidacion.security.payload.JwtResponse;
import com.team1.obvalidacion.security.payload.LoginRequest;
import com.team1.obvalidacion.security.payload.MessageResponse;
import com.team1.obvalidacion.security.payload.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<List<User>> findAll();

    ResponseEntity<User> findOneById(Long id);

    ResponseEntity<MessageResponse> register(RegisterRequest request);

    ResponseEntity<JwtResponse> login(LoginRequest request);

    ResponseEntity<User> patch(Long id, Map<Object, Object> fields);

    ResponseEntity delete(Long id);

    ResponseEntity deleteAll();

}
