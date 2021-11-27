package com.team1.obvalidacion.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.team1.obvalidacion.entities.BackId;
import com.team1.obvalidacion.entities.FrontId;
import com.team1.obvalidacion.entities.User;
import com.team1.obvalidacion.repositories.BackIdRepository;
import com.team1.obvalidacion.repositories.FrontIdRepository;
import com.team1.obvalidacion.repositories.UserRepository;
import com.team1.obvalidacion.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class CloudinaryService {

    @Value("${jwt.header.string}")
    public String HEADER_STRING;

    @Value("${jwt.token.prefix}")
    public String TOKEN_PREFIX;

    Cloudinary cloudinary;

    @Autowired
    private FrontIdRepository frontIdRepository;

    @Autowired
    private BackIdRepository backIdRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private Map<String, String> valuesMap = new HashMap<>();

    public CloudinaryService(){
        valuesMap.put("cloud_name", "open-bootcamp-equipo-4");
        valuesMap.put("api_key", "314627757939635");
        valuesMap.put("api_secret", "lz5VRIHwmvZQBHQPLAVVsj0nhZM");
        cloudinary = new Cloudinary(valuesMap);
    }

    public boolean uploadFrontId(MultipartFile multipartFile, HttpServletRequest req) throws IOException {
        //Retrieving username from Token
        String authToken = req.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,"");
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        if (userRepository.findByUsername(username).get().getFrontId() == null){
            File file = convert(multipartFile);
            FrontId frontId = new FrontId();
            Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            // Save frontId data from image
            result.forEach((key, value) -> {
                if (Objects.equals(key, "secure_url")) frontId.setUrl((String) value);
                if (Objects.equals(key, "public_id")) frontId.setCloudinaryId((String) value);
            });
            frontIdRepository.save(frontId);
            //Connecting picture with proper User
            Optional<User> user = userRepository.findByUsername(username);
            user.get().setFrontId(frontId);
            userRepository.save(user.get());
            return true;
        }
        return false;
    }

    public Map deleteFrontId(String id) throws IOException {
        //Connecting picture with proper User
        FrontId frontId = frontIdRepository.findByCloudinaryId(id);
        Optional<User> user = userRepository.findByFrontId(frontId);
        if (user.isPresent()) {
            user.get().setFrontId(null);
            userRepository.save(user.get());
        }
        //Deleting frontId
        Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        frontIdRepository.delete(frontIdRepository.findByCloudinaryId(id));
        return result;
    }

    public Map uploadBackId(MultipartFile multipartFile, HttpServletRequest req) throws IOException {
        File file = convert(multipartFile);
        BackId backId = new BackId();
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        // Save frontId data from image
        result.forEach((key, value) -> {
            if (Objects.equals(key, "secure_url")) backId.setUrl((String) value);
            if (Objects.equals(key, "public_id")) backId.setCloudinaryId((String) value);
        });
        backIdRepository.save(backId);
        //Retrieving username from Token
        String authToken = req.getHeader(HEADER_STRING).replace(TOKEN_PREFIX,"");
        String username = jwtTokenUtil.getUsernameFromToken(authToken);
        //Connecting picture with proper User
        Optional<User> user = userRepository.findByUsername(username);
        user.get().setBackId(backId);
        userRepository.save(user.get());
        return result;
    }

    public Map deleteBackId(String id) throws IOException {
        //Connecting picture with proper User
        BackId backId = backIdRepository.findByCloudinaryId(id);
        Optional<User> user = userRepository.findByBackId(backId);
        user.get().setBackId(null);
        userRepository.save(user.get());
        //Deleting frontId
        Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        backIdRepository.delete(backIdRepository.findByCloudinaryId(id));
        return result;
    }
    
    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }
}
