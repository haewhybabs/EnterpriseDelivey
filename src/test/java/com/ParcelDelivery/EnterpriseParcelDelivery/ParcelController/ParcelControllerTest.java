package com.ParcelDelivery.EnterpriseParcelDelivery.ParcelController;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.ParcelDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.dto.RecipientAddressDTO;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Parcel;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ParcelControllerTest {
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
    @Order(5)
    public void testCreateParcel(){
        String url =baseUrl+"parcel/add";
        String token = senderToken;
        // Create the DTO
        ParcelDTO dto = new ParcelDTO();
        dto.setName("My Teseting Product");
        dto.setDescription("My Testing Description");

        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);

        // Create the request entity
        HttpEntity<ParcelDTO> request = new HttpEntity<>(dto, headers);

        // Send the request and get the response
        Parcel response = restTemplate.postForObject(url, request, Parcel.class);

        // Assert that the response is as expected
        assertEquals("My Teseting Product",response.getName());
    }

}