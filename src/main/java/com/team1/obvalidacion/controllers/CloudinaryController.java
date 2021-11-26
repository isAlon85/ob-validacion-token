package com.team1.obvalidacion.controllers;

import com.team1.obvalidacion.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/cloudinary")
@CrossOrigin
public class CloudinaryController {

    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping("/uploadfront")
    public ResponseEntity<Map> uploadFrontId (@RequestParam MultipartFile multipartFile, HttpServletRequest req) throws IOException {
        Map result = cloudinaryService.uploadFrontId(multipartFile, req);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deletefront/{id}")
    public ResponseEntity<Map> uploadFrontId (@PathVariable("id") String id) throws IOException {
        Map result = cloudinaryService.deleteFrontId(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PostMapping("/uploadback")
    public ResponseEntity<Map> uploadbackId (@RequestParam MultipartFile multipartFile, HttpServletRequest req) throws IOException {
        Map result = cloudinaryService.uploadBackId(multipartFile, req);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/deleteback/{id}")
    public ResponseEntity<Map> uploadbackId (@PathVariable("id") String id) throws IOException {
        Map result = cloudinaryService.deleteBackId(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
