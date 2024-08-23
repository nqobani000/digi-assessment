package com.main.agency_booking.hotel.manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import com.main.agency_booking.hotel.data.dto.HotelDto;
import com.main.agency_booking.hotel.data.model.Hotel;
import com.main.agency_booking.hotel.repository.HotelRepository;
import com.main.agency_booking.utils.ValidatorUtils;
import jakarta.validation.ValidationException;

public class HotelManagerImpTest {
    
    private static final String EMPTY_STRING = "";
    private static final String HOTEL_NAME = "Hotel Name";
    private static final String HOTEL_ADDRESS = "Hotel Address";
    private static final String INVALID_EMAIL = "invalid-email";
    private static final String VALID_EMAIL = "hotel@example.com";
    private static final String INVALID_PHONE = "123";
    private static final String VALID_PHONE = "0712345678";
    
    private static final String NAME_VALIDATION_MESSAGE = ValidatorUtils.HotelDtoValidationMessages.NAME_VALIDATION_MESSAGE;
    private static final String ADDRESS_VALIDATION_MESSAGE = ValidatorUtils.HotelDtoValidationMessages.ADDRESS_VALIDATION_MESSAGE;
    private static final String EMAIL_VALIDATION_MESSAGE = ValidatorUtils.HotelDtoValidationMessages.EMAIL_VALIDATION_MESSAGE;
    private static final String CELL_NO_VALIDATION_MESSAGE = ValidatorUtils.HotelDtoValidationMessages.CELL_NO_VALIDATION_MESSAGE;
    
    private HotelManagerImp hotelManager;
    private HotelRepository hotelRepository;
    
    @BeforeEach
    public void setUp() {
        hotelRepository = mock(HotelRepository.class);
        hotelManager = new HotelManagerImp(hotelRepository);
    }
    
    @Test
    @Description("Test that if a Hotel Object is not valid, appropriate exceptions and messages are thrown.")
    public void validate() {
        validate_shouldThrowValidationException_whenNameIsNullOrEmpty();
        validate_shouldThrowValidationException_whenAddressIsNullOrEmpty();
        validate_shouldThrowValidationException_whenEmailIsNullOrInvalid();
        validate_shouldThrowValidationException_whenPhoneIsNullOrInvalid();
        validate_shouldNotThrow_whenAllFieldsAreValid();
    }
    
    public void validate_shouldThrowValidationException_whenNameIsNullOrEmpty() {
        Hotel hotel = new Hotel();
        hotel.setName(EMPTY_STRING);
        
        ValidationException exception = assertThrows(ValidationException.class, () -> hotelManager.validate(hotel));
        assertEquals(NAME_VALIDATION_MESSAGE, exception.getMessage());
    }
    
    public void validate_shouldThrowValidationException_whenAddressIsNullOrEmpty() {
        Hotel hotel = new Hotel();
        hotel.setName(HOTEL_NAME);
        hotel.setAddress(EMPTY_STRING);
        
        ValidationException exception = assertThrows(ValidationException.class, () -> hotelManager.validate(hotel));
        assertEquals(ADDRESS_VALIDATION_MESSAGE, exception.getMessage());
    }
    
    public void validate_shouldThrowValidationException_whenEmailIsNullOrInvalid() {
        Hotel hotel = new Hotel();
        hotel.setName(HOTEL_NAME);
        hotel.setAddress(HOTEL_ADDRESS);
        hotel.setEmail(INVALID_EMAIL);
        
        ValidationException exception = assertThrows(ValidationException.class, () -> hotelManager.validate(hotel));
        assertEquals(EMAIL_VALIDATION_MESSAGE, exception.getMessage());
    }
    
    public void validate_shouldThrowValidationException_whenPhoneIsNullOrInvalid() {
        Hotel hotel = new Hotel();
        hotel.setName(HOTEL_NAME);
        hotel.setAddress(HOTEL_ADDRESS);
        hotel.setEmail(VALID_EMAIL);
        hotel.setPhone(INVALID_PHONE);
        
        ValidationException exception = assertThrows(ValidationException.class, () -> hotelManager.validate(hotel));
        assertEquals(CELL_NO_VALIDATION_MESSAGE, exception.getMessage());
    }
    
    public void validate_shouldNotThrow_whenAllFieldsAreValid() {
        Hotel hotel = new Hotel();
        hotel.setName(HOTEL_NAME);
        hotel.setAddress(HOTEL_ADDRESS);
        hotel.setEmail(VALID_EMAIL);
        hotel.setPhone(VALID_PHONE);
        
        assertDoesNotThrow(() -> hotelManager.validate(hotel));
    }
    
    @Test
    public void findHotelById_shouldReturnNull_whenHotelDoesNotExist() {
        Long hotelId = 1L;
        
        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());
        
        Hotel result = hotelManager.findHotelById(hotelId);
        
        assertNull(result);
        verify(hotelRepository).findById(hotelId);
    }
    
    @Test
    public void addHotel_shouldSaveHotel_whenHotelDtoIsValid() {
        HotelDto dto = new HotelDto();
        dto.setName(HOTEL_NAME);
        dto.setAddress(HOTEL_ADDRESS);
        dto.setEmail(VALID_EMAIL);
        dto.setPhone(VALID_PHONE);
        
        Hotel savedHotel = new Hotel(dto);
        
        when(hotelRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.empty());
        when(hotelRepository.save(any(Hotel.class))).thenReturn(savedHotel);
        
        Hotel result = hotelManager.addHotel(dto);
        
        assertNotNull(result);
        assertEquals(HOTEL_NAME, result.getName());
        assertEquals(HOTEL_ADDRESS, result.getAddress());
        assertEquals(VALID_EMAIL, result.getEmail());
        assertEquals(VALID_PHONE, result.getPhone());
        verify(hotelRepository).findByEmail(VALID_EMAIL);
        verify(hotelRepository).save(any(Hotel.class));
    }
    
}
