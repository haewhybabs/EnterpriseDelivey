package com.parceldelivery.core.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RoleControllerIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:latest");

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createRole_returnsCreatedAndPersists() throws Exception {
        mockMvc.perform(post("/api/roles")
                .with(user("testuser"))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"IntegrationTestRole\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("IntegrationTestRole"))
                .andExpect(jsonPath("$.id").exists());

    }

    @Test
    void getRoleById_returns404_whenNotFound() throws Exception {
        mockMvc.perform(get("/api/roles/999999")
                .with(user("testuser")))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Role not found with id: 999999"));
    }

}
