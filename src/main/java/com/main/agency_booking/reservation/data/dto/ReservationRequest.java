package com.main.agency_booking.reservation.data.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {
    
    /*
     * Ideally, I would use spring security to get the presently authenticated user.
     * For the sake of time and reduced complexity, I will not be implementing spring security,
     * and because of that, I will be manually sending some sensitive user info through the controller (i.e, IDs, Emails)
     * */
    
    @PositiveOrZero
    @JsonProperty("hotel_id")
    private long hotelId;
    
    @Email
    @JsonProperty("guest_email")
    private String guestEmail;
    
    @NotNull
    @FutureOrPresent
    @JsonProperty("check_in_date_time")
    private LocalDateTime checkIn;
    
    @NotNull
    @FutureOrPresent
    @JsonProperty("check_out_date_time")
    private LocalDateTime checkOut;
}
