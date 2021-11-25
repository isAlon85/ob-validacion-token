package com.team1.obvalidacion.repositories;

import com.team1.obvalidacion.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findRoleByName(String name);

}
