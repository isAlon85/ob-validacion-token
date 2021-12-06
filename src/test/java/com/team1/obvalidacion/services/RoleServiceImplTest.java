package com.team1.obvalidacion.services;

import com.team1.obvalidacion.entities.Role;
import com.team1.obvalidacion.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RoleServiceImplTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Test
    void findByIdTest() {
        Optional<Role> role1 = roleRepository.findById(1L);
        Optional<Role> role2 = roleRepository.findById(2L);

        assertEquals("USER", role1.get().getName());
        assertNotEquals("ADMIN", role1.get().getName());
        assertEquals("ADMIN", role2.get().getName());
        assertNotEquals("USER", role2.get().getName());
        assertEquals(2, roleRepository.count());
    }

    @Test
    void findByNameTest() {
        assertEquals("USER", roleService.findByName("USER").getName());
        assertEquals("ADMIN", roleService.findByName("ADMIN").getName());
        assertNotEquals("ADMIN", roleService.findByName("USER").getName());
        assertNotEquals("USER", roleService.findByName("ADMIN").getName());
        assertEquals(2, roleRepository.count());
    }
}
