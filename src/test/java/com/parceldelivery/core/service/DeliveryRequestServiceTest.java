package com.parceldelivery.core.service;

import com.parceldelivery.core.dto.AssignDriverRequestDTO;
import com.parceldelivery.core.dto.DeliveryRequestRequestDTO;
import com.parceldelivery.core.dto.DeliveryRequestResponseDTO;
import com.parceldelivery.core.dto.UpdateDeliveryStatusRequestDTO;
import com.parceldelivery.core.entity.DeliveryRequest;
import com.parceldelivery.core.entity.DeliveryStatus;
import com.parceldelivery.core.entity.Driver;
import com.parceldelivery.core.entity.Parcel;
import com.parceldelivery.core.entity.RecipientAddress;
import com.parceldelivery.core.entity.User;
import com.parceldelivery.core.exception.BadRequestException;
import com.parceldelivery.core.exception.ResourceNotFoundException;
import com.parceldelivery.core.mapper.DeliveryRequestMapper;
import com.parceldelivery.core.repository.DeliveryRequestRepository;
import com.parceldelivery.core.repository.DeliveryStatusRepository;
import com.parceldelivery.core.repository.DriverRepository;
import com.parceldelivery.core.repository.ParcelRepository;
import com.parceldelivery.core.repository.RecipientAddressRepository;
import com.parceldelivery.core.security.CurrentUserProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryRequestServiceTest {

    @Mock
    private DeliveryRequestRepository deliveryRequestRepository;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @Mock
    private DeliveryStatusRepository deliveryStatusRepository;

    @Mock
    private ParcelRepository parcelRepository;

    @Mock
    private RecipientAddressRepository recipientAddressRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DeliveryRequestMapper deliveryRequestMapper;

    @InjectMocks
    private DeliveryRequestService deliveryRequestService;

    private DeliveryRequestRequestDTO validRequestDto() {
        DeliveryRequestRequestDTO dto = new DeliveryRequestRequestDTO();
        dto.setParcelId(1L);
        dto.setRecipientAddressId(1L);
        dto.setSenderAddress("10 Downing Street");
        return dto;
    }

    @Test
    void createDeliveryRequest_succeeds_whenEverythingExists() {
        // Arrange
        User user = new User();
        user.setId(1L);

        DeliveryStatus status = new DeliveryStatus();
        status.setId(1L);
        status.setName("Awaiting delivery");

        Parcel parcel = new Parcel();
        parcel.setId(1L);

        RecipientAddress recipientAddress = new RecipientAddress();
        recipientAddress.setId(1L);

        DeliveryRequest saved = new DeliveryRequest();
        saved.setId(1L);

        DeliveryRequestResponseDTO expectedDto = new DeliveryRequestResponseDTO();
        expectedDto.setId(1L);
        expectedDto.setDeliveryStatusId(1L);

        // Fakes "User user = currentUserProvider.getCurrentUser();" - without this, it would return null.
        when(currentUserProvider.getCurrentUser()).thenReturn(user);
        // Fakes the default-status lookup; wrapped in Optional.of(...) because the real method returns Optional.
        when(deliveryStatusRepository.findByName("Awaiting delivery")).thenReturn(Optional.of(status));
        // Fakes "parcelRepository.findById(dto.getParcelId())" - dto.getParcelId() is 1L, see validRequestDto().
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(parcel));
        // Fakes "recipientAddressRepository.findById(dto.getRecipientAddressId())", also 1L.
        when(recipientAddressRepository.findById(1L)).thenReturn(Optional.of(recipientAddress));
        // Fakes the final save() call. any(DeliveryRequest.class) matches whatever object the method
        // builds internally with "new DeliveryRequest()" - we can't predict that exact instance.
        when(deliveryRequestRepository.save(any(DeliveryRequest.class))).thenReturn(saved);
        // Fakes the last line of the method: turning the saved entity into the response DTO we assert on.
        when(deliveryRequestMapper.toResponseDTO(saved)).thenReturn(expectedDto);

        // Act
        DeliveryRequestResponseDTO result = deliveryRequestService.createDeliveryRequest(validRequestDto());

        // Assert
        assertThat(result.getDeliveryStatusId()).isEqualTo(1L);
    }

    @Test
    void createDeliveryRequest_throwsIllegalState_whenDefaultStatusIsNotSeeded() {
        // Arrange
        when(currentUserProvider.getCurrentUser()).thenReturn(new User());
        // Optional.empty() here is the whole point of this test: simulates the seed data being missing,
        // which should make the real method throw IllegalStateException before touching anything else.
        when(deliveryStatusRepository.findByName("Awaiting delivery")).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(IllegalStateException.class,
                () -> deliveryRequestService.createDeliveryRequest(validRequestDto()));
    }

    @Test
    void createDeliveryRequest_throwsResourceNotFound_whenParcelIsMissing() {
        // Arrange
        when(currentUserProvider.getCurrentUser()).thenReturn(new User());
        // Status lookup has to succeed here - we're testing the parcel check, not the status check,
        // so everything before it needs to pass cleanly.
        when(deliveryStatusRepository.findByName("Awaiting delivery"))
                .thenReturn(Optional.of(new DeliveryStatus()));
        // The actual thing this test is about: parcel genuinely not found -> Optional.empty().
        when(parcelRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> deliveryRequestService.createDeliveryRequest(validRequestDto()));
    }

    @Test
    void createDeliveryRequest_throwsResourceNotFound_whenRecipientAddressIsMissing() {
        // Arrange
        when(currentUserProvider.getCurrentUser()).thenReturn(new User());
        when(deliveryStatusRepository.findByName("Awaiting delivery"))
                .thenReturn(Optional.of(new DeliveryStatus()));
        // Parcel lookup also has to succeed - this test is specifically about the recipient address check.
        when(parcelRepository.findById(1L)).thenReturn(Optional.of(new Parcel()));
        // The actual thing this test is about: recipient address genuinely not found.
        when(recipientAddressRepository.findById(1L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> deliveryRequestService.createDeliveryRequest(validRequestDto()));
    }

    @Test
    void assignDriver_succeeds_whenDeliveryRequestAndDriverExist() {
        // Arrange
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        Driver driver = new Driver();
        driver.setId(1L);

        AssignDriverRequestDTO dto = new AssignDriverRequestDTO();
        dto.setDriverId(1L);

        DeliveryRequestResponseDTO expectedDto = new DeliveryRequestResponseDTO();
        expectedDto.setDriverId(1L);

        // Fakes "deliveryRequestRepository.findById(deliveryRequestId)" - the first lookup in assignDriver().
        when(deliveryRequestRepository.findById(1L)).thenReturn(Optional.of(deliveryRequest));
        // Fakes "driverRepository.findById(dto.getDriverId())" - the second lookup.
        when(driverRepository.findById(1L)).thenReturn(Optional.of(driver));
        // Here we pass the exact same object (deliveryRequest) instead of any(...), since the method
        // mutates and saves that specific instance rather than constructing a new one - we know exactly
        // which object reference will be passed to save().
        when(deliveryRequestRepository.save(deliveryRequest)).thenReturn(deliveryRequest);
        when(deliveryRequestMapper.toResponseDTO(deliveryRequest)).thenReturn(expectedDto);

        // Act
        DeliveryRequestResponseDTO result = deliveryRequestService.assignDriver(1L, dto);

        // Assert
        assertThat(result.getDriverId()).isEqualTo(1L);
    }

    @Test
    void assignDriver_throwsResourceNotFound_whenDeliveryRequestIsMissing() {
        // Arrange
        // The delivery request lookup itself fails here - method should throw before ever reaching
        // the driver lookup, so driverRepository is never stubbed in this test at all.
        when(deliveryRequestRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> deliveryRequestService.assignDriver(99L, new AssignDriverRequestDTO()));
    }

    @Test
    void assignDriver_throwsResourceNotFound_whenDriverIsMissing() {
        // Arrange
        AssignDriverRequestDTO dto = new AssignDriverRequestDTO();
        dto.setDriverId(99L);

        // Delivery request must be found first so execution actually reaches the driver lookup below.
        when(deliveryRequestRepository.findById(1L)).thenReturn(Optional.of(new DeliveryRequest()));
        // This is the lookup the test is actually about: driver genuinely not found.
        when(driverRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class, () -> deliveryRequestService.assignDriver(1L, dto));
    }

    @Test
    void updateStatus_succeeds_whenStatusNameIsValid() {
        // Arrange
        DeliveryRequest deliveryRequest = new DeliveryRequest();
        DeliveryStatus status = new DeliveryStatus();
        status.setId(2L);
        status.setName("Processing");

        UpdateDeliveryStatusRequestDTO dto = new UpdateDeliveryStatusRequestDTO();
        dto.setStatusName("Processing");

        DeliveryRequestResponseDTO expectedDto = new DeliveryRequestResponseDTO();
        expectedDto.setDeliveryStatusId(2L);

        // Fakes "deliveryRequestRepository.findById(deliveryRequestId)" in updateStatus().
        when(deliveryRequestRepository.findById(1L)).thenReturn(Optional.of(deliveryRequest));
        // Fakes "deliveryStatusRepository.findByName(dto.getStatusName())" - the status client asked for.
        when(deliveryStatusRepository.findByName("Processing")).thenReturn(Optional.of(status));
        // Same exact-object reasoning as assignDriver() above - the method mutates this specific instance.
        when(deliveryRequestRepository.save(deliveryRequest)).thenReturn(deliveryRequest);
        when(deliveryRequestMapper.toResponseDTO(deliveryRequest)).thenReturn(expectedDto);

        // Act
        DeliveryRequestResponseDTO result = deliveryRequestService.updateStatus(1L, dto);

        // Assert
        assertThat(result.getDeliveryStatusId()).isEqualTo(2L);
    }

    @Test
    void updateStatus_throwsBadRequest_whenStatusNameIsInvalid() {
        // Arrange
        UpdateDeliveryStatusRequestDTO dto = new UpdateDeliveryStatusRequestDTO();
        dto.setStatusName("Teleported");

        // Delivery request must exist so execution reaches the status-name check below.
        when(deliveryRequestRepository.findById(1L)).thenReturn(Optional.of(new DeliveryRequest()));
        // The actual thing this test is about: "Teleported" isn't a real status -> BadRequestException.
        when(deliveryStatusRepository.findByName("Teleported")).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(BadRequestException.class, () -> deliveryRequestService.updateStatus(1L, dto));
    }

    @Test
    void updateStatus_throwsResourceNotFound_whenDeliveryRequestIsMissing() {
        // Arrange
        // No deliveryStatusRepository stub needed - the method should throw before ever reaching it.
        when(deliveryRequestRepository.findById(99L)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(ResourceNotFoundException.class,
                () -> deliveryRequestService.updateStatus(99L, new UpdateDeliveryStatusRequestDTO()));
    }
}
