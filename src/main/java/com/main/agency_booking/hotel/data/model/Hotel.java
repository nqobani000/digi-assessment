package com.main.agency_booking.hotel.data.model;

import com.main.agency_booking.hotel.data.dto.HotelDto;
import com.main.agency_booking.utils.PersistedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "hotel")
public class Hotel extends PersistedEntity {
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String address;
    
    @Column(unique = true, nullable = false)
    private String phone;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    public Hotel(HotelDto hotelDto) {
        super();
        name = hotelDto.getName();
        address = hotelDto.getAddress();
        phone = hotelDto.getPhone();
        email = hotelDto.getEmail();
    }
    
    public HotelDto getDto() {
        HotelDto dto = new HotelDto();
        dto.setName(name);
        dto.setAddress(address);
        dto.setPhone(phone);
        dto.setEmail(email);
        return dto;
    }
}
