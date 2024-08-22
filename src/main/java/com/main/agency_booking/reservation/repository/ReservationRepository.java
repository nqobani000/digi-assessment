package com.main.agency_booking.reservation.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.main.agency_booking.reservation.data.model.Reservation;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    List<Reservation> findByGuestIdAndDeletedIsFalse(Long guestId);
    Page<Reservation> findByHotelId(Long hotelId, Pageable pageable);
}
