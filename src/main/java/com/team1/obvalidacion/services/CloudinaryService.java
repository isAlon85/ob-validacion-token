package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.BackId;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public interface CloudinaryService {

    boolean uploadFrontId(MultipartFile multipartFile, HttpServletRequest req) throws IOException;

    boolean deleteFrontId(String id) throws IOException;

    boolean uploadBackId(MultipartFile multipartFile, HttpServletRequest req) throws IOException;

    boolean deleteBackId(String id) throws IOException;

    File convert(MultipartFile multipartFile) throws IOException;

}