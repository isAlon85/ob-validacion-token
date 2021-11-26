package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.BackId;

import java.util.Optional;

public interface BackIdService {

    Optional<BackId> findById(Long id);

}