package com.team1.obvalidacion.controllers;

import com.team1.obvalidacion.services.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/cloudinary")
@CrossOrigin
public class CloudinaryController {

    @Autowired
    CloudinaryService cloudinaryService;

    @PostMapping("/upload")
    public ResponseEntity<Map> upload (@RequestParam MultipartFile multipartFile) throws IOException {
        Map result = cloudinaryService.upload(multipartFile);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map> upload (@PathVariable("id") String id) throws IOException {
        Map result = cloudinaryService.delete(id);
        return new ResponseEntity(result, HttpStatus.OK);
    }
}
