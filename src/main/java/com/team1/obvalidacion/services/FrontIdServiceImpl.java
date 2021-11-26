package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.FrontId;
import com.team1.obvalidacion.repositories.FrontIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FrontIdServiceImpl implements FrontIdService{

    @Autowired
    private FrontIdRepository frontIdRepository;

    @Override
    public Optional<FrontId> findById(Long id) {
        return frontIdRepository.findById(id);
    }

}

