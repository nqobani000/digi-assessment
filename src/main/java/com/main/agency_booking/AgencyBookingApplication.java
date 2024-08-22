package com.main.agency_booking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.main.agency_booking.hotel.data.dto.HotelDto;
import com.main.agency_booking.hotel.manager.HotelManagerImp;
import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class AgencyBookingApplication {
    
    HotelManagerImp hotelManagerImp;
    
    @Autowired
    public AgencyBookingApplication(HotelManagerImp hotelManagerImp) {
        this.hotelManagerImp = hotelManagerImp;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(AgencyBookingApplication.class, args);
    }
    
    @PostConstruct
    public void populateDummyHotels() {
        List<HotelDto> hotels = new ArrayList<>();
        hotels.add(new HotelDto("The Grand Hotel", "123 Main St, Springfield", "+27611111111", "info@grandhotel.com"));
        hotels.add(new HotelDto("Ocean View Resort", "456 Ocean Dr, Malibu", "+27611111112", "contact@oceanview.com"));
        hotels.add(new HotelDto("Mountain Lodge", "789 Alpine Rd, Aspen", "0611111113", "reservations@mountainlodge.com"));
        hotels.add(new HotelDto("City Center Inn", "101 City Square, New York", "0711111413", "info@citycenterinn.com"));
        hotels.add(new HotelDto("Desert Oasis", "202 Mirage Ave, Las Vegas", "0736781453", "contact@desertoasis.com"));
        hotels.add(new HotelDto("Lakeside Hotel", "303 Lake Rd, Lake Tahoe", "+27611711119", "reservations@lakeside.com"));
        hotels.add(new HotelDto("Forest Retreat", "404 Forest Ln, Portland", "+27613578111", "info@forestretreat.com"));
        hotels.add(new HotelDto("Beachfront Paradise", "505 Beach Blvd, Miami", "0762579473", "contact@beachfrontparadise.com"));
        hotels.add(new HotelDto("Historic Manor", "707 Heritage Dr, Boston", "+27635191119", "info@historicmanor.com"));
        
        for (HotelDto hotel : hotels) {
            hotelManagerImp.addHotel(hotel);
        }
    }
}
