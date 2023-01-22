package com.ParcelDelivery.EnterpriseParcelDelivery.UserController;

import com.ParcelDelivery.EnterpriseParcelDelivery.dto.*;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.Role;
import com.ParcelDelivery.EnterpriseParcelDelivery.entity.User;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.RoleRepository;
import com.ParcelDelivery.EnterpriseParcelDelivery.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

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
    @Order(1)
    public void testCreateSenderAccount(){
//        userRepository.deleteAll();
        UserDTO user = new UserDTO();
        user.setName("Ayo");;
        user.setPassword("passPassword");
        user.setEmail("babson@gmail.com");
        UserDTO response = restTemplate.postForObject(baseUrl+"user/add",user,UserDTO.class);
        assertEquals("Ayo",response.getName());
    }

    @Test
    @Order(2)
    public void testCreateNewDriver(){
        String url = baseUrl + "driver/add";
        DriverDTO user = new DriverDTO();
        user.setName("Ayo Driver");
        user.setPassword("passPassword");
        user.setEmail("driver@gmail.com");
        user.setAddress("New Driver Address");
        user.setPhone_number("08033223322");
        DriverDTO response = restTemplate.postForObject(url,user,DriverDTO.class);
        assertEquals("driver@gmail.com",response.getEmail());
    }

    @Test
    @Order(3)
    public void testUserAuthentication(){
        String url = baseUrl + "authenticate";
        AuthDTO dto = new AuthDTO();
        dto.setEmail("babson@gmail.com");
        dto.setPassword("passPassword");
        AuthResponseDTO response = restTemplate.postForObject(url,dto,AuthResponseDTO.class);
        log.info("TOKEN {}",response.getToken());
        assertEquals("babson@gmail.com",response.getEmail());
    }

    @Test
    @Order(4)
    public void testCreateAdminAccount(){
        String url = baseUrl + "user/admin/add";
        String token = adminToken;
        UserDTO dto = new UserDTO();
        dto.setEmail("babsonAdmin@gmail.com");
        dto.setPassword("passPassword");
        dto.setName("Admin Kuller");

        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",token);

        // Create the request entity
        HttpEntity<UserDTO> request = new HttpEntity<>(dto, headers);

        AuthResponseDTO response = restTemplate.postForObject(url,request,AuthResponseDTO.class);
        assertEquals("babsonAdmin@gmail.com",response.getEmail());
    }



}