package com.main.agency_booking.reservation.data.model;


import java.time.LocalDateTime;

import com.main.agency_booking.guest.data.model.Guest;
import com.main.agency_booking.hotel.data.model.Hotel;
import com.main.agency_booking.reservation.data.dto.ReservationDto;
import com.main.agency_booking.utils.PersistedEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "reservation")
public class Reservation extends PersistedEntity {
    
    @JoinColumn(name = "guest_id")
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Guest guest;
    
    @JoinColumn(name = "hotel_id")
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    private Hotel hotel;
    
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    
    public ReservationDto getDto() {
        ReservationDto dto = new ReservationDto();
        dto.setGuest(guest.getDto());
        dto.setHotel(hotel.getDto());
        dto.setCheckIn(checkIn);
        dto.setCheckOut(checkOut);
        return dto;
    }
}
