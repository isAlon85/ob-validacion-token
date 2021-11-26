package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.BackId;
import com.team1.obvalidacion.repositories.BackIdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BackIdServiceImpl implements BackIdService{

    @Autowired
    private BackIdRepository backIdRepository;

    @Override
    public Optional<BackId> findById(Long id) {
        return backIdRepository.findById(id);
    }

}

