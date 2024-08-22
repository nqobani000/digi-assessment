package com.main.agency_booking.guest.data.model;

import java.time.LocalDateTime;

import com.main.agency_booking.guest.data.dto.GuestDto;
import com.main.agency_booking.utils.PersistedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@Entity(name = "guest")
public class Guest extends PersistedEntity {
    
    @Column(nullable = false)
    private String firstName;
    
    @Column(nullable = false)
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    public Guest(GuestDto guestDto) {
        super();
        firstName = guestDto.getFirstName();
        lastName = guestDto.getLastName();
        email = guestDto.getEmail();
        phone = guestDto.getPhone();
    }
    
    public GuestDto getDto() {
        GuestDto dto = new GuestDto();
        dto.setFirstName(firstName);
        dto.setLastName(lastName);
        dto.setEmail(email);
        dto.setPhone(phone);
        return dto;
    }
    
    public void update(Guest guest) {
        firstName = guest.getFirstName();
        lastName = guest.getLastName();
        email = guest.getEmail();
        phone = guest.getPhone();
        dateModified = LocalDateTime.now();
    }
}
