package com.ParcelDelivery.EnterpriseParcelDelivery.DeliveryRequestController;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.*;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DeliveryRequestControllerTest {

    @LocalServerPort
    private int port;
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
    @Order(7)
    public void testCreateDeliveryRequest(){
        String url =baseUrl+"request/create";
        String token =senderToken;
        // Create the DTO
        DeliveryRequestDTO dto = new DeliveryRequestDTO();
        dto.setDelivery_date(LocalDateTime.parse("2023-02-20T12:00:00"));
        dto.setParcel_id(1);
        dto.setRecipient_address_id(1);
        dto.setSender_address("gbolla 23");
        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        // Create the request entity
        HttpEntity<DeliveryRequestDTO> request = new HttpEntity<>(dto, headers);
        // Send the request and get the response
        TestResponseDTO response = restTemplate.postForObject(url,request,TestResponseDTO.class);
        // Assert that the response is as expected
        assertEquals("gbolla 23",response.getSender_address());
    }

    @Test
    @Order(8)
    public void testAssignDriverToARequest(){
        String url =baseUrl+"request/assign-driver";
        String token = adminToken;
        // Create the DTO
        DeliveryRequestDTO dto = new DeliveryRequestDTO();
        dto.setId(1);
        dto.setDriver_id(1);
        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        // Create the request entity
        HttpEntity<DeliveryRequestDTO> request = new HttpEntity<>(dto, headers);
        // Send the request and get the response
        TestResponseDTO response = restTemplate.exchange(url, HttpMethod.PUT, request, TestResponseDTO.class).getBody();
        // Assert that the response is as expected
        assertEquals(1,response.getId());
    }

    @Test
    @Order(9)
    public void testUpdateDeliveryStatus(){
        String url =baseUrl+"request/update-status";
        String token =adminToken;
        // Create the DTO
        DeliveryRequestDTO dto = new DeliveryRequestDTO();
        dto.setId(1);
        dto.setDelivery_status_id(2);
        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        // Create the request entity
        HttpEntity<DeliveryRequestDTO> request = new HttpEntity<>(dto, headers);
        // Send the request and get the response
        TestResponseDTO response = restTemplate.exchange(url, HttpMethod.PUT, request, TestResponseDTO.class).getBody();
        // Assert that the response is as expected
        assertEquals(1,response.getId());
    }

    @Test
    @Order(10)
    public void testRateADeliveryRequest(){
        String url =baseUrl+"rating/create";
        String token =senderToken;
        // Create the DTO
        RatingDTO dto = new RatingDTO();
        dto.setDelivery_request_id(1);
        dto.setSender_rating(3);
        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        // Create the request entity
        HttpEntity<RatingDTO> request = new HttpEntity<>(dto, headers);
        // Send the request and get the response
        RatingDTO response = restTemplate.postForObject(url,request,RatingDTO.class);
        // Assert that the response is as expected
        assertEquals(1,response.getId());
    }

    @Test
    @Order(11)
    public void trackADeliveryRequest(){
        int deliveryRequestId =1;
        String url =baseUrl+"request/"+deliveryRequestId;
        String token =senderToken;
        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity request = new HttpEntity(headers);
        // Send the request and get the response
        TestResponseDTO response = restTemplate.exchange(url, HttpMethod.GET, request, TestResponseDTO.class).getBody();
        // Assert that the response is as expected
        assertEquals(1,response.getId());
    }

    @Test
    @Order(12)
    public void ViewDeliveryRequestRating(){
        int deliveryRequestId =1;
        String url =baseUrl+"rating/view/"+deliveryRequestId;
        String token =senderToken;
        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity request = new HttpEntity(headers);
        // Send the request and get the response
        TestResponseDTO response = restTemplate.exchange(url, HttpMethod.GET, request, TestResponseDTO.class).getBody();
        // Assert that the response is as expected
        assertEquals(1,response.getId());
    }






}