package com.main.agency_booking.reservation.manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Description;

import com.main.agency_booking.guest.data.model.Guest;
import com.main.agency_booking.guest.manager.GuestManagerImp;
import com.main.agency_booking.hotel.data.model.Hotel;
import com.main.agency_booking.hotel.manager.HotelManagerImp;
import com.main.agency_booking.reservation.data.dto.ReservationRequest;
import com.main.agency_booking.reservation.data.model.Reservation;
import com.main.agency_booking.reservation.repository.ReservationRepository;
import jakarta.validation.ValidationException;


class ReservationManagerImpTest {
    
    private static final String GUEST_EMAIL = "guest@gmail.com";
    private static final Long HOTEL_ID = 1L;
    private static final LocalDateTime CHECK_IN = LocalDateTime.now();
    private static final LocalDateTime CHECK_OUT = CHECK_IN.plusDays(2);
    
    private ReservationManagerImp reservationManager;
    private ReservationRepository reservationRepository;
    private GuestManagerImp guestManager;
    private HotelManagerImp hotelManager;
    
    @BeforeEach
    public void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        guestManager = mock(GuestManagerImp.class);
        hotelManager = mock(HotelManagerImp.class);
        reservationManager = new ReservationManagerImp(reservationRepository, guestManager, hotelManager);
    }
    
    @Test
    @Description("Test successful reservation creation.")
    public void createReservation_shouldCreateReservation_whenRequestIsValid() {
        Guest guest = new Guest();
        guest.setEmail(GUEST_EMAIL);
        
        Hotel hotel = new Hotel();
        hotel.setId(HOTEL_ID);
        
        ReservationRequest request = new ReservationRequest();
        request.setGuestEmail(GUEST_EMAIL);
        request.setHotelId(HOTEL_ID);
        request.setCheckIn(CHECK_IN);
        request.setCheckOut(CHECK_OUT);
        
        when(guestManager.findGuestByEmail(GUEST_EMAIL)).thenReturn(guest);
        when(hotelManager.findHotelById(HOTEL_ID)).thenReturn(hotel);
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        Reservation result = reservationManager.createReservation(request);
        
        assertNotNull(result);
        assertEquals(guest, result.getGuest());
        assertEquals(hotel, result.getHotel());
        assertEquals(CHECK_IN, result.getCheckIn());
        assertEquals(CHECK_OUT, result.getCheckOut());
    }
    
    @Test
    @Description("Test for a successful deletion of a reservation.")
    public void deleteReservation_shouldMarkReservationAsDeleted_whenReservationExists() {
        long reservationId = 1L;
        Reservation reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setDeleted(false);
        
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        reservationManager.deleteReservation(reservationId);
        
        assertTrue(reservation.isDeleted());
    }
    
    @Test
    @Description("Test for deletion of a reservation that does not exist.")
    public void deleteReservation_shouldThrowValidationException_whenReservationDoesNotExist() {
        long reservationId = 1L;
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());
        ValidationException exception = assertThrows(ValidationException.class, () -> reservationManager.deleteReservation(reservationId));
        assertEquals("Reservation with id " + reservationId + " does not exist", exception.getMessage());
    }
}
