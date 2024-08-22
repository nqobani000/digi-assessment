package com.main.agency_booking.hotel.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    
    private String name;
    
    private String address;
    
    private String phone;
    
    private String email;
}
