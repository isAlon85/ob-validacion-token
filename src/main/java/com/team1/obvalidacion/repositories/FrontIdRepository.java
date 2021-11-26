package com.team1.obvalidacion.repositories;

import com.team1.obvalidacion.entities.FrontId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface FrontIdRepository extends CrudRepository<FrontId, Long> {

    Optional<FrontId> findById(Long id);

    Boolean existsByCloudinaryId(String cloudinaryId);

    FrontId findByCloudinaryId(String cloudinaryId);
}
