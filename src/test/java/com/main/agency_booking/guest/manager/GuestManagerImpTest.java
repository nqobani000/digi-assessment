package com.main.agency_booking.guest.manager;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Description;

import com.main.agency_booking.guest.data.dto.GuestDto;
import com.main.agency_booking.guest.data.model.Guest;
import com.main.agency_booking.guest.repository.GuestRepository;
import jakarta.validation.ValidationException;


class GuestManagerImpTest {
    private static final String VALID_FIRST_NAME = "Nqobani";
    private static final String VALID_LAST_NAME = "Nhlengethwa";
    private static final String VALID_EMAIL = "email@gmail.com";
    private static final String VALID_PHONE = "0712345678";
    private static final String DUMMY_EMAIL = "dummy@email.com";
    private static final String INVALID_PHONE = "1234567890";
    
    @Mock
    GuestRepository guestRepository;
    
    @InjectMocks
    private GuestManagerImp guestManager;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    @Description("Test that if a valid guest object is provided, it is saved successfully")
    void testAddGuest_ValidGuest() {
        GuestDto validGuest = new GuestDto();
        validGuest.setFirstName(VALID_FIRST_NAME);
        validGuest.setLastName(VALID_LAST_NAME);
        validGuest.setEmail(VALID_EMAIL);
        validGuest.setPhone(VALID_PHONE);
        
        Guest guestEntity = new Guest(validGuest);
        
        when(guestManager.findGuestByEmail(validGuest.getEmail())).thenReturn(null);
        when(guestRepository.findByEmail(validGuest.getEmail())).thenReturn(Optional.empty());
        when(guestRepository.save(any(Guest.class))).thenReturn(guestEntity);
        
        Guest guest = guestManager.addGuest(validGuest);
        assertEquals(guestEntity.getEmail(), guest.getEmail());
    }
    
    @Test
    @Description("Validate that a valid Guest object does not throw any exceptions")
    void testValidate_ValidGuest() {
        Guest guest = new Guest();
        guest.setFirstName(VALID_FIRST_NAME);
        guest.setLastName(VALID_LAST_NAME);
        guest.setEmail(VALID_EMAIL);
        guest.setPhone(VALID_PHONE);
        
        assertDoesNotThrow(() -> guestManager.validate(guest));
    }
    
    @Test
    @Description("Test that if an email already exists, we can find the Guest it belongs to.")
    void testFindGuestByEmail_EmailExists() {
        String email = DUMMY_EMAIL;
        Guest guest = new Guest();
        guest.setEmail(email);
        
        when(guestRepository.findByEmail(email)).thenReturn(Optional.of(guest));
        Guest foundGuest = guestManager.findGuestByEmail(email);
        
        assertNotNull(foundGuest);
        assertEquals(email, foundGuest.getEmail());
        verify(guestRepository).findByEmail(email);
    }
    
    @Test
    @Description("Validate that an invalid Guest object throws an exception")
    void testValidate_InvalidGuest() {
        Guest guest = new Guest();
        guest.setFirstName(null);
        assertThrows(ValidationException.class, () -> guestManager.validate(guest));
        guest.setFirstName(VALID_FIRST_NAME);
        assertThrows(ValidationException.class, () -> guestManager.validate(guest));
        guest.setLastName(VALID_LAST_NAME);
        assertThrows(ValidationException.class, () -> guestManager.validate(guest));
        guest.setEmail(VALID_EMAIL);
        assertThrows(ValidationException.class, () -> guestManager.validate(guest));
        guest.setPhone(INVALID_PHONE);
        assertThrows(ValidationException.class, () -> guestManager.validate(guest));
    }
}
