package com.team1.obvalidacion.repositories;

import com.team1.obvalidacion.entities.BackId;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface BackIdRepository extends CrudRepository<BackId, Long> {

    Optional<BackId> findById(Long id);

    Boolean existsByCloudinaryId(String cloudinaryId);

    BackId findByCloudinaryId(String cloudinaryId);
}
