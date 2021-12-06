package com.team1.obvalidacion.services;

import com.team1.obvalidacion.repositories.BackIdRepository;
import com.team1.obvalidacion.repositories.FrontIdRepository;
import com.team1.obvalidacion.repositories.UserRepository;
import com.team1.obvalidacion.security.payload.RegisterRequest;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudinaryServiceImplTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private FrontIdRepository frontIdRepository;

    @Autowired
    private BackIdRepository backIdRepository;

    @Autowired
    private UserService userService;

    @Before
    public void beforeCloudinary(){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail("mail@test.com");
        registerRequest.setName("nombre1");
        registerRequest.setSurname("apellido1");
        registerRequest.setPassword("Test1234");
        userService.register(registerRequest);
    }

    @Test
    @Order(1)
    void uploadFrontId() throws IOException {
        beforeCloudinary();

        File fileItem = new File("dni.jpg");
        FileInputStream input = new FileInputStream(fileItem);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                fileItem.getName(), "image/jpg", IOUtils.toByteArray(input));
        assertEquals(true, cloudinaryService.uploadFrontId(multipartFile, "mail@test.com"));

        // Upload twice
        assertEquals(false, cloudinaryService.uploadFrontId(multipartFile, "mail@test.com"));
    }

    @Test
    @Order(2)
    void deleteFrontId() throws IOException {
        String cloudinaryId = userRepository.findByUsername("mail@test.com").get().getFrontId().getCloudinaryId();

        assertEquals(true, cloudinaryService.deleteFrontId(cloudinaryId));

        //Delete twice
        assertEquals(false, cloudinaryService.deleteFrontId(cloudinaryId));
    }

    @Test
    @Order(1)
    void uploadBackId() throws IOException {
        beforeCloudinary();

        File fileItem = new File("dni.jpg");
        FileInputStream input = new FileInputStream(fileItem);
        MultipartFile multipartFile = new MockMultipartFile("fileItem",
                fileItem.getName(), "image/jpg", IOUtils.toByteArray(input));
        assertEquals(true, cloudinaryService.uploadBackId(multipartFile, "mail@test.com"));

        // Upload twice
        assertEquals(false, cloudinaryService.uploadBackId(multipartFile, "mail@test.com"));
    }

    @Test
    @Order(2)
    void deleteBackId() throws IOException {
        String cloudinaryId = userRepository.findByUsername("mail@test.com").get().getBackId().getCloudinaryId();

        assertEquals(true, cloudinaryService.deleteBackId(cloudinaryId));

        //Delete twice
        assertEquals(false, cloudinaryService.deleteBackId(cloudinaryId));
    }
}