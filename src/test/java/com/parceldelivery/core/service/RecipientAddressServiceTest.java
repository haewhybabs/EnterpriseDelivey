package com.parceldelivery.core.service;

import com.parceldelivery.core.dto.RecipientAddressRequestDTO;
import com.parceldelivery.core.dto.RecipientAddressResponseDTO;
import com.parceldelivery.core.entity.RecipientAddress;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.mapper.RecipientAddressMapper;
import com.parceldelivery.core.repository.RecipientAddressRepository;
import com.parceldelivery.core.security.CurrentUserProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecipientAddressServiceTest {

    @Mock
    private RecipientAddressRepository recipientAddressRepository;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @Mock
    private RecipientAddressMapper recipientAddressMapper;

    @InjectMocks
    private RecipientAddressService recipientAddressService;

    @Test
    void createRecipientAddress_assignsAddressToTheAuthenticatedUser() {
        // Arrange
        User user = new User();
        user.setId(1L);

        RecipientAddressRequestDTO dto = new RecipientAddressRequestDTO();
        dto.setAddress("221B Baker Street");
        dto.setRecipientPhoneNumber("+447700900456");
        dto.setRecipientEmail("recipient@example.com");

        RecipientAddress saved = new RecipientAddress();
        saved.setId(1L);
        saved.setUser(user);

        RecipientAddressResponseDTO expectedDto = new RecipientAddressResponseDTO();
        expectedDto.setId(1L);
        expectedDto.setUserId(1L);
        expectedDto.setRecipientEmail("recipient@example.com");

        when(currentUserProvider.getCurrentUser()).thenReturn(user);
        when(recipientAddressRepository.save(any(RecipientAddress.class))).thenReturn(saved);
        when(recipientAddressMapper.toResponseDTO(saved)).thenReturn(expectedDto);

        // Act
        RecipientAddressResponseDTO result = recipientAddressService.createRecipientAddress(dto);

        // Assert
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getRecipientEmail()).isEqualTo("recipient@example.com");
    }
}
