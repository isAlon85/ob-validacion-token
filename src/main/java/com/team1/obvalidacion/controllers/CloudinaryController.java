package com.team1.obvalidacion.controllers;

import com.team1.obvalidacion.security.payload.MessageResponse;
import com.team1.obvalidacion.services.CloudinaryServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/cloudinary")
@CrossOrigin
public class CloudinaryController {

    private final Logger log = LoggerFactory.getLogger(CloudinaryController.class);

    @Autowired
    CloudinaryServiceImpl cloudinaryServiceImpl;

    @PostMapping("/uploadfront")
    @ApiOperation("Upload front ID image")
    public ResponseEntity<MessageResponse> uploadFrontId (@RequestParam MultipartFile multipartFile, @CurrentSecurityContext(expression="authentication?.name")
            String username) throws IOException {
        boolean result = cloudinaryServiceImpl.uploadFrontId(multipartFile, username);
        if (result) {
            log.info("Front ID uploaded successful");
            return new ResponseEntity(new MessageResponse("Front ID uploaded successful"), HttpStatus.OK);
        } else {
            log.error("Front ID already uploaded");
            return new ResponseEntity(new MessageResponse("Front ID already uploaded"), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletefront/{id}")
    @ApiOperation("Delete front ID image")
    public ResponseEntity<Map> uploadFrontId (@PathVariable("id") String id) throws IOException {
        boolean result = cloudinaryServiceImpl.deleteFrontId(id);
        if (result) {
            log.info("Front ID deleted successful");
            return new ResponseEntity(new MessageResponse("Front ID deleted successful"), HttpStatus.OK);
        } else {
            log.error("Front ID doesn't exist");
            return new ResponseEntity(new MessageResponse("Front ID doesn't exist"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/uploadback")
    @ApiOperation("Upload back ID image")
    public ResponseEntity<MessageResponse> uploadBackId (@RequestParam MultipartFile multipartFile, @CurrentSecurityContext(expression="authentication?.name")
            String username) throws IOException {
        boolean result = cloudinaryServiceImpl.uploadBackId(multipartFile, username);
        if (result) {
            log.info("Back ID uploaded successful");
            return new ResponseEntity(new MessageResponse("Back ID uploaded successful"), HttpStatus.OK);
        } else {
            log.error("Back ID already uploaded");
            return new ResponseEntity(new MessageResponse("Back ID already uploaded"), HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteback/{id}")
    @ApiOperation("Delete back ID image")
    public ResponseEntity<Map> uploadBackId (@PathVariable("id") String id) throws IOException {
        boolean result = cloudinaryServiceImpl.deleteBackId(id);
        if (result) {
            log.info("Back ID deleted successful");
            return new ResponseEntity(new MessageResponse("Back ID deleted successful"), HttpStatus.OK);
        } else {
            log.error("Back ID doesn't exist");
            return new ResponseEntity(new MessageResponse("Back ID doesn't exist"), HttpStatus.BAD_REQUEST);
        }
    }
}
