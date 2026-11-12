package com.parceldelivery.core.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.parceldelivery.core.entity.Role;

import org.testcontainers.postgresql.PostgreSQLContainer;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleRepositoryTest {
    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByName_returnsRole_whenExists() {
        Role role = new Role();
        role.setName("TestOnlyRole");
        roleRepository.save(role);

        Optional<Role> found = roleRepository.findByName("TestOnlyRole");
        assertTrue(found.isPresent());
        assertEquals("TestOnlyRole", found.get().getName());
    }

    @Test
    void findByName_returnsEmpty_whenNotExists() {
        Optional<Role> found = roleRepository.findByName("NonExistent");

        assertThat(found).isEmpty();
    }
}
