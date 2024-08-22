package com.main.agency_booking.reservation.manager;

import java.time.LocalDateTime;
import java.util.List;

import com.main.agency_booking.reservation.data.dto.ReservationRequest;
import com.main.agency_booking.reservation.data.model.Reservation;


public interface ReservationManager {
    
    List<Reservation> findAllReservations(long guestId);
    
    void deleteReservation(long reservationId);
    
    Reservation createReservation(ReservationRequest request);
    
    Reservation updateReservationDate(Long reservationId, LocalDateTime checkInDate, LocalDateTime checkOutDate);
    
    List<Reservation> getAllHotelReservations(long hotelId, int page, int size);
}
