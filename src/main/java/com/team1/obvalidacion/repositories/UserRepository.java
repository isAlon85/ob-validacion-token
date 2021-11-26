package com.team1.obvalidacion.repositories;

import com.team1.obvalidacion.entities.BackId;
import com.team1.obvalidacion.entities.FrontId;
import com.team1.obvalidacion.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByFrontId(FrontId frontId);

    Boolean existsByFrontId(FrontId frontId);

    Optional<User> findByBackId(BackId backId);

    Boolean existsByBackId(BackId backId);

}