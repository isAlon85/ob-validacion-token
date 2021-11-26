package com.team1.obvalidacion.services;


import com.team1.obvalidacion.entities.FrontId;
import com.team1.obvalidacion.security.payload.MessageResponse;
import com.team1.obvalidacion.security.payload.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface FrontIdService {

    Optional<FrontId> findById(Long id);

}