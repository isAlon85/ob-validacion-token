package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.Role;
import com.team1.obvalidacion.entities.User;
import com.team1.obvalidacion.repositories.UserRepository;
import com.team1.obvalidacion.security.jwt.JwtTokenUtil;
import com.team1.obvalidacion.security.payload.JwtResponse;
import com.team1.obvalidacion.security.payload.LoginRequest;
import com.team1.obvalidacion.security.payload.MessageResponse;
import com.team1.obvalidacion.security.payload.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(AuthenticationManager authManager,
                          UserRepository userRepository,
                          PasswordEncoder encoder,
                          JwtTokenUtil jwtTokenUtil){
        this.authManager = authManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    @Override
    public ResponseEntity<List<User>> findAll(){
        if (userRepository.count() == 0)
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public ResponseEntity<User> findOneById(Long id){
        Optional<User> regUserOpt = userRepository.findById(id);

        if (regUserOpt.isPresent())
            return ResponseEntity.ok(regUserOpt.get());
        else
            return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest signUpRequest) {

        // Check 2: email
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        User user = new User(encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail(), signUpRequest.getName(), signUpRequest.getSurname());

        Role role = roleService.findByName("USER");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);
        user.setValidated(false);

        if (user.getEmail().split("@")[1].equals("validation.com")){
            role = roleService.findByName("ADMIN");
            roleSet.add(role);
            user.setValidated(true);
        }
        user.setRoles(roleSet);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateToken(authentication);
        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        // UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt, user.get().getName(), user.get().getSurname(), user.get().getRoles()));
    }


    @Override
    public ResponseEntity<User> update(User user){

        if (user.getId() == null)
            return ResponseEntity.badRequest().build();

        if (!userRepository.existsById(user.getId()))
            return ResponseEntity.notFound().build();

//        for (int i = 0; i < regUserRepository.count(); i++){
//            if (Objects.equals(regUser.getEmail(), regUserRepository.findAll().get(i).getEmail())) {
//                return ResponseEntity.badRequest().build();
//            }
//        }
//
//        for (int i = 0; i < regUserRepository.count(); i++){
//            if (Objects.equals(regUser.getUsername(), regUserRepository.findAll().get(i).getUsername())) {
//                return ResponseEntity.badRequest().build();
//            }
//        }

        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        User result = userRepository.save(user);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity delete(Long id){

        if (!userRepository.existsById(id))
            return ResponseEntity.notFound().build();

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity deleteAll(){

        if (userRepository.count() == 0)
            return ResponseEntity.notFound().build();

        userRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
}

