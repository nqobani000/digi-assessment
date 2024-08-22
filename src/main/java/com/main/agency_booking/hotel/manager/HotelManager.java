package com.main.agency_booking.hotel.manager;

import java.util.List;

import com.main.agency_booking.hotel.data.dto.HotelDto;
import com.main.agency_booking.hotel.data.model.Hotel;


public interface HotelManager {
    
    List<Hotel> getHotelsPaginated(int page, int size);
    Hotel addHotel(HotelDto dto);
    Hotel findHotelById(Long id);
}
