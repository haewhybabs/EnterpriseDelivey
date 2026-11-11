package com.parceldelivery.core.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.parceldelivery.core.entity.DeliveryStatus;
import com.parceldelivery.core.entity.Role;
import com.parceldelivery.core.repository.DeliveryStatusRepository;
import com.parceldelivery.core.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Exercises the full core business flow end to end, through real HTTP requests,
 * using a genuine JWT obtained from a real register + login - not a mock user.
 * This is required because every endpoint below (Parcel, RecipientAddress, Driver,
 * DeliveryRequest) resolves identity via CurrentUserProvider, which looks up a real
 * row by the authenticated principal's email. A fake @WithMockUser/with(user(...))
 * principal has no backing database row, so it would fail with "Authenticated user
 * not found" the moment any of these services ran.
 */
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CoreBusinessFlowIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:17");

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DeliveryStatusRepository deliveryStatusRepository;

    // Fresh Testcontainers Postgres has tables but no seed data - no Flyway yet,
    // so lookup rows the old project seeded by hand have to be seeded here instead.
    @BeforeEach
    void seedLookupData() {
        if (roleRepository.findByName("Sender").isEmpty()) {
            Role role = new Role();
            role.setName("Sender");
            roleRepository.save(role);
        }
        if (deliveryStatusRepository.findByName("Awaiting delivery").isEmpty()) {
            DeliveryStatus status = new DeliveryStatus();
            status.setName("Awaiting delivery");
            deliveryStatusRepository.save(status);
        }
        if (deliveryStatusRepository.findByName("Delivered").isEmpty()) {
            DeliveryStatus status = new DeliveryStatus();
            status.setName("Delivered");
            deliveryStatusRepository.save(status);
        }
    }

    private String fieldAsText(MvcResult result, String field) throws Exception {
        JsonNode body = objectMapper.readTree(result.getResponse().getContentAsString());
        return body.get(field).asText();
    }

    @Test
    void fullCoreFlow_registerThroughRating_worksEndToEndWithARealToken() throws Exception {
        // 1. Register a real user
        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Bob Marley","email":"bob.flow@example.com","password":"reggae123","roleName":"Sender"}
                                """))
                .andExpect(status().isCreated());

        // 2. Log in to get a genuine JWT
        MvcResult loginResult = mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"bob.flow@example.com","password":"reggae123"}
                                """))
                .andExpect(status().isOk())
                .andReturn();
        String token = fieldAsText(loginResult, "token");
        String authHeader = "Bearer " + token;

        // 3. Create a parcel as the authenticated user - no userId anywhere in the request
        MvcResult parcelResult = mockMvc.perform(post("/api/parcels")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Guitar","description":"Acoustic guitar, handle with care"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").exists())
                .andReturn();
        Long parcelId = Long.valueOf(fieldAsText(parcelResult, "id"));

        // 4. Add a recipient address
        MvcResult addressResult = mockMvc.perform(post("/api/recipient-addresses")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"address":"45 Marley Rd, Kingston","recipientPhoneNumber":"+18765551234","recipientEmail":"recipient@example.com"}
                                """))
                .andExpect(status().isCreated())
                .andReturn();
        Long recipientAddressId = Long.valueOf(fieldAsText(addressResult, "id"));

        // 5. Become a driver (same user - no role check enforced by design)
        MvcResult driverResult = mockMvc.perform(post("/api/drivers/create")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"phoneNumber":"+18765559999","address":"45 Marley Rd, Kingston"}
                                """))
                .andExpect(status().isCreated())
                .andReturn();
        Long driverId = Long.valueOf(fieldAsText(driverResult, "id"));

        // 6. Submit a delivery request - status should default automatically, no driver yet
        MvcResult deliveryRequestResult = mockMvc.perform(post("/api/delivery-requests/create")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"parcelId\":" + parcelId
                                + ",\"recipientAddressId\":" + recipientAddressId
                                + ",\"senderAddress\":\"10 Downing Street\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.driverId").doesNotExist())
                .andReturn();
        Long deliveryRequestId = Long.valueOf(fieldAsText(deliveryRequestResult, "id"));

        // 7. Assign the driver
        mockMvc.perform(patch("/api/delivery-requests/" + deliveryRequestId + "/assign-driver")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"driverId\":" + driverId + "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.driverId").value(driverId));

        // 8. Move it through to Delivered
        mockMvc.perform(patch("/api/delivery-requests/" + deliveryRequestId + "/status")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"statusName\":\"Delivered\"}"))
                .andExpect(status().isOk());

        // 9. Rate it - should succeed now that it's Delivered
        mockMvc.perform(post("/api/ratings")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"deliveryRequestId\":" + deliveryRequestId + ",\"senderRating\":5}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.senderRating").value(5));

        // 10. Rating it again should be rejected as a duplicate
        mockMvc.perform(post("/api/ratings")
                        .header("Authorization", authHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"deliveryRequestId\":" + deliveryRequestId + ",\"senderRating\":3}"))
                .andExpect(status().isBadRequest());
    }
}
