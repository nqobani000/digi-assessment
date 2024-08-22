package com.main.agency_booking.reservation.data.dto;

import java.time.LocalDateTime;

import com.main.agency_booking.guest.data.dto.GuestDto;
import com.main.agency_booking.hotel.data.dto.HotelDto;
import com.main.agency_booking.reservation.data.model.Reservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ReservationDto {
    
    private GuestDto guest;
    private HotelDto hotel;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    
    public ReservationDto(Reservation reservation) {
        guest = reservation.getGuest().getDto();
        hotel = reservation.getHotel().getDto();
        checkIn = reservation.getCheckIn();
        checkOut = reservation.getCheckOut();
    }
}
