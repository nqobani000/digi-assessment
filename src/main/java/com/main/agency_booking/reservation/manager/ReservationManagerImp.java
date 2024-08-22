package com.main.agency_booking.reservation.manager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.main.agency_booking.guest.data.model.Guest;
import com.main.agency_booking.guest.manager.GuestManagerImp;
import com.main.agency_booking.hotel.data.model.Hotel;
import com.main.agency_booking.hotel.manager.HotelManagerImp;
import com.main.agency_booking.reservation.data.dto.ReservationRequest;
import com.main.agency_booking.reservation.data.model.Reservation;
import com.main.agency_booking.reservation.repository.ReservationRepository;
import com.main.agency_booking.utils.ValidatorUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class ReservationManagerImp implements ReservationManager {
    
    private final ReservationRepository reservationRepository;
    private final GuestManagerImp guestManager;
    private final HotelManagerImp hotelManager;
    
    @Autowired
    public ReservationManagerImp(
            ReservationRepository reservationRepository,
            GuestManagerImp guestManager,
            HotelManagerImp hotelManager) {
        this.reservationRepository = reservationRepository;
        this.guestManager = guestManager;
        this.hotelManager = hotelManager;
    }
    
    @Override
    public List<Reservation> findAllReservations(long guestId) {
        Guest guest = guestManager.findGuestById(guestId);
        if (guest == null) {
            throw new ValidationException("Guest not found");
        }
        return reservationRepository.findByGuestIdAndDeletedIsFalse(guestId);
    }
    
    @Override
    @Transactional
    public void deleteReservation(long reservationId) {
        Optional<Reservation> optional = reservationRepository.findById(reservationId);
        if (optional.isPresent()) {
            Reservation reservation = optional.get();
            reservation.setDeleted(true);
        } else {
            throw new ValidationException("Reservation with id " + reservationId + " does not exist");
        }
    }
    
    @Override
    public Reservation createReservation(ReservationRequest request) {
        Guest guest = guestManager.findGuestByEmail(request.getGuestEmail());
        if (guest == null) {
            throw new ValidationException(ValidatorUtils.GuestDtoValidationMessages.GUEST_NOT_FOUND_VALIDATION_MESSAGE);
        }
        
        Hotel hotel = hotelManager.findHotelById(request.getHotelId());
        if (hotel == null) {
            throw new ValidationException(ValidatorUtils.HotelDtoValidationMessages.HOTEL_NOT_FOUND_VALIDATION_MESSAGE);
        }
        
        Reservation reservation = new Reservation();
        reservation.setGuest(guest);
        reservation.setHotel(hotel);
        reservation.setCheckIn(request.getCheckIn());
        reservation.setCheckOut(request.getCheckOut());
        
        log.info("Reservation created: {}", reservation);
        return reservationRepository.save(reservation);
    }
    
    @Override
    @Transactional
    public Reservation updateReservationDate(Long reservationId, LocalDateTime checkIn, LocalDateTime checkOut) {
        Optional<Reservation> optional = reservationRepository.findById(reservationId);
        if (optional.isPresent()) {
            Reservation reservation = optional.get();
            reservation.setCheckIn(checkIn);
            reservation.setCheckOut(checkOut);
            return reservation;
        } else {
            throw new ValidationException("Reservation with id " + reservationId + " does not exist");
        }
    }
    
    @Override
    public List<Reservation> getAllHotelReservations(long hotelId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Reservation> reservationPage = reservationRepository.findByHotelId(hotelId, pageRequest);
        return reservationPage.getContent();
    }
}
