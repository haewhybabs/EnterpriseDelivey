package com.ParcelDelivery.EnterpriseParcelDelivery.RecipientAddressController;


import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RecipientAddressDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecipientAddressControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private RoleRepository roleRepository;
    private String baseUrl = "http://localhost";
    private String adminToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWJhbG9sYWlzYWFjMkBnbWFpbC5jb20iLCJleHAiOjE2NzYxOTM5NTYsImlhdCI6MTY3NDM3OTU1Nn0.4JKgOZiZPlqwrfOIHndSXK08ugBBB9JwzMr7rqrBwiA";
    private String senderToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJiYWJzb25AZ21haWwuY29tIiwiZXhwIjoxNjc2MTk0MDUzLCJpYXQiOjE2NzQzNzk2NTN9.CENxi2ohj6ItNO5c2lRNptin6MyGNpuomHVBp_wlqJ8";
    private static RestTemplate restTemplate;
    @BeforeAll
    public static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp(){

        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/");
    }

    @Test
    @Order(6)
    public void testCreateRecipientAddress(){
        String url =baseUrl+"recipient-address/save";
        String token = senderToken;
        // Create the DTO
        RecipientAddressDTO dto = new RecipientAddressDTO();
        dto.setRecipient_phone_number("08022332233");
        dto.setAddress("21 Bello street");
        dto.setRecipient_email("ajanagbe@gmail.com");

        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        // Create the request entity
        HttpEntity<RecipientAddressDTO> request = new HttpEntity<>(dto, headers);

        // Send the request and get the response
        RecipientAddressDTO response = restTemplate.postForObject(url, request, RecipientAddressDTO.class);

        // Assert that the response is as expected
        assertEquals("ajanagbe@gmail.com",response.getRecipient_email());
    }
}