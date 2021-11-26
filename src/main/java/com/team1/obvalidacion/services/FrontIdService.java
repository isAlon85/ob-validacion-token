package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.FrontId;

import java.util.Optional;

public interface FrontIdService {

    Optional<FrontId> findById(Long id);

}